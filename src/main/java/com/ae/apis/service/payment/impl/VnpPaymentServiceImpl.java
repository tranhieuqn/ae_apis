package com.ae.apis.service.payment.impl;

import com.ae.apis.entity.PaymentVNP;
import com.ae.apis.entity.enums.PaymentStatus;
import com.ae.apis.repository.VnpPaymentRepository;
import com.ae.apis.service.payment.VnpPaymentService;
import com.ae.apis.service.payment.common.CommonUtils;
import com.ae.apis.service.payment.common.MessageUtils;
import com.ae.apis.service.payment.common.VNPayEnv;
import com.ae.apis.service.payment.common.VNPayProperties;
import com.ae.apis.service.payment.dto.*;
import com.ae.apis.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ae.apis.common.Constants.VNPAY_COMMAND.REFUND;
import static com.ae.apis.common.Constants.VNPAY_REFUND_TYPE.ALL_AMOUNT;

@Service
public class VnpPaymentServiceImpl implements VnpPaymentService {
    private static final Logger logger = LoggerFactory.getLogger(VnpPaymentService.class);

    private static final String VNPAY_MESSAGE_PREFIX = "vnpay.";

    private static final String PAYMENT_FAILED_MSG = "Giao dịch thất bại.";

    @Autowired
    private VnpPaymentRepository vnpPaymentRepository;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private VNPayProperties vnpProperties;

    @Autowired
    protected RestTemplate restTemplate;

    private final DateTimeUtils dateTimeUtil = DateTimeUtils.getInstance();

    @Override
    @Transactional
    public VNPPaymentResponse createPayment(VNPPaymentRequest payload) throws UnsupportedEncodingException {
        VNPayEnv env = vnpProperties.getVNPayEnv();
        String vnp_Version = env.getVnpVersion();
        String vnp_TmnCode = env.getVnpTmnCode();
        String vnp_HashSecret = env.getVnpHashSecret();
        String vnp_ReturnUrl = env.getVnpReturnUrl();
        String vnp_PayUrl = env.getVnpPayUrl();

        String vnp_Command = "pay";
        String vnp_CurrCode = "VND";
        String vnp_Locale = "vn";

        String vnp_OrderInfo = payload.getOrderInfo();
        String vnp_TxnRef = payload.getVnpTxnRef();
        String vnp_orderType = payload.getOrderType();
        String vnp_IpAddr = payload.getIpClient();

        Long amount = payload.getAmount().longValue() * 100;
        Long accountId = payload.getAccountId();

        // Save vnp payment information
        PaymentVNP paymentInfo = new PaymentVNP();
        paymentInfo.setPayment(payload.getPayment());
        paymentInfo.setVnpIpAddr(vnp_IpAddr);
        paymentInfo.setVnpTxnRef(vnp_TxnRef);
        paymentInfo.setVnpTmnCode(vnp_TmnCode);
        paymentInfo.setVnpAmount(payload.getAmount());
        paymentInfo.setVnpCommand(vnp_Command);
        paymentInfo.setVnpCreateDate(dateTimeUtil.getNow());
        paymentInfo.setVnpCurrCode(vnp_CurrCode);
        paymentInfo.setVnpLocale(vnp_Locale);
        paymentInfo.setVnpVersion(vnp_Version);
        paymentInfo.setVnpOrderType(payload.getOrderType());
        paymentInfo.setVnpOrderInfo(vnp_OrderInfo);
        paymentInfo.setAccountId(accountId);

        PaymentVNP persist = vnpPaymentRepository.save(paymentInfo);


        //Build data to hash and queryString
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnp_Version);
        vnpParams.put("vnp_Command", vnp_Command);
        vnpParams.put("vnp_TmnCode", vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", vnp_CurrCode);
        vnpParams.put("vnp_TxnRef", vnp_TxnRef);
        vnpParams.put("vnp_OrderInfo", vnp_OrderInfo);
        vnpParams.put("vnp_OrderType", vnp_orderType);
        vnpParams.put("vnp_Locale", vnp_Locale);
        vnpParams.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr", vnp_IpAddr);
        String vnp_CreateDate = dateTimeUtil.getNowStr("yyyyMMddHHmmss");
        vnpParams.put("vnp_CreateDate", vnp_CreateDate);

        String queryUrl = CommonUtils.buildQueryString(vnpParams, false);
        String hashData = CommonUtils.buildQueryString(vnpParams, false);
        String vnpSecureHash = CommonUtils.Sha256(String.format("%s%s", vnp_HashSecret, hashData));
        String paymentUrl = CommonUtils.buildVnpPaymentUrl(vnp_PayUrl, queryUrl, vnpSecureHash);

        VNPPaymentResponse result = new VNPPaymentResponse();
        result.setPaymentInfo(persist);
        result.setPaymentUrl(paymentUrl);

        return result;
    }

    @Override
    public PaymentIPNProcessResponse paymentIPNProcess(Map<String, String> payload) {
        try {
            if (!validChecksum(payload)) {
                return PaymentIPNProcessResponse.ofInvalidChecksum();
            }

            String vnpTxnRef = payload.get("vnp_TxnRef");
            Optional<PaymentVNP> paymentOpt = vnpPaymentRepository.findByVnpTxnRef(vnpTxnRef);
            if (paymentOpt.isEmpty()) {
                return PaymentIPNProcessResponse.ofOrderNotFound();
            }

            PaymentVNP paymentVNP = paymentOpt.get();
            if (paymentVNP.getPayment().alreadyProcessed()) {
                return PaymentIPNProcessResponse.ofOrderAlreadyConfirmed();
            }

            BigDecimal ipnAmount = new BigDecimal(payload.get("vnp_Amount")).divide(new BigDecimal(100));
            if (paymentVNP.getVnpAmount().compareTo(ipnAmount) != 0) {
                return PaymentIPNProcessResponse.ofInvalidAmount();
            }

            String ipnResCode = payload.get("vnp_ResponseCode");
            if ("00".equals(ipnResCode)) {
                switch (paymentVNP.getVnpOrderType()) {
                    case "BOOKING":
                        //TODO: do somethings
                        break;
                    default:
                        throw new Exception("Order type not found.");
                }
            }

            savePaymentInfo(paymentVNP, payload, ipnResCode);

            return PaymentIPNProcessResponse.ofOrderConfirmSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return PaymentIPNProcessResponse.ofUnknownError();
        }
    }

    @Override
    public CheckPaymentProcessResponse checkPaymentProcess(Map<String, String> payload) {
        try {
            if (!validChecksum(payload)) {
                return CheckPaymentProcessResponse.ofInvalidChecksum();
            }

            String vnpTxnRef = payload.get("vnp_TxnRef");
            Optional<PaymentVNP> paymentOpt = vnpPaymentRepository.findByVnpTxnRef(vnpTxnRef);
            if (paymentOpt.isEmpty()) {
                return CheckPaymentProcessResponse.ofPaymentFailed();
            }

            PaymentVNP payment = paymentOpt.get();

            BigDecimal ipnAmount = new BigDecimal(payload.get("vnp_Amount")).divide(new BigDecimal(100));
            if (payment.getVnpAmount().compareTo(ipnAmount) != 0) {
                return CheckPaymentProcessResponse.ofPaymentFailed();
            }

            String ipnResCode = payload.get("vnp_ResponseCode");
            if (ipnResCode == null) {
                return CheckPaymentProcessResponse.ofPaymentFailed();
            }

            String responseMsg = messageUtils.getMessage(VNPAY_MESSAGE_PREFIX.concat(ipnResCode));
            if (responseMsg == null) {
                responseMsg = PAYMENT_FAILED_MSG;
            }

            CheckPaymentProcessResponse response = new CheckPaymentProcessResponse();
            response.setCode(ipnResCode);
            response.setMessage(responseMsg);

            if ("00".equals(ipnResCode)) {
                CheckPaymentProcessResponse.PaymentProcessDataResponse dataRes = new CheckPaymentProcessResponse.PaymentProcessDataResponse();
                dataRes.setTransactionNo(payload.get("vnp_TransactionNo"));
                dataRes.setAmount(ipnAmount);
                dataRes.setPayDate(CommonUtils.StringToDate(payload.get("vnp_PayDate"), "yyyyMMddHHmmss"));

                response.setData(dataRes);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return CheckPaymentProcessResponse.ofPaymentFailed();
        }
    }

    @Override
    public String doRefund(VNPayRefundRequest payload) throws Exception {
        VNPayEnv env = vnpProperties.getVNPayEnv();
        String vnp_Version = env.getVnpVersion();
        String vnp_TmnCode = env.getVnpTmnCode();
        String vnp_HashSecret = env.getVnpHashSecret();
        String vnp_PayUrl = env.getVnpPayUrl();
        String vnp_RefundEmail = env.getVnpRefundEmail();

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnp_Version);
        vnpParams.put("vnp_Command", REFUND);
        vnpParams.put("vnp_TmnCode", vnp_TmnCode);
        vnpParams.put("vnp_TransactionType", ALL_AMOUNT);
        vnpParams.put("vnp_CreateBy", vnp_RefundEmail);
        vnpParams.put("vnp_TxnRef", payload.getVnpTxnRef());
        vnpParams.put("vnp_Amount", String.valueOf(payload.getAmount() * 100));
        vnpParams.put("vnp_OrderInfo", "Hoàn trả Phí giao dịch");
        InetAddress inetAddress = InetAddress.getLocalHost();
        vnpParams.put("vnp_IpAddr", inetAddress.getHostAddress());
        vnpParams.put("vnp_TransDate", payload.getVnpTxnDateStr());
        vnpParams.put("vnp_CreateDate", dateTimeUtil.getNowStr("yyyyMMddHHmmss"));

        String queryUrl = CommonUtils.buildQueryString(vnpParams, false);
        String hashData = CommonUtils.buildQueryString(vnpParams, false);
        String vnpSecureHash = CommonUtils.Sha256(vnp_HashSecret + hashData);
        String refundUrl = CommonUtils.buildVnpPaymentUrl(vnp_PayUrl, queryUrl, vnpSecureHash);

        String vnpResponse = restTemplate.getForObject(refundUrl, String.class);

        return vnpResponse;
    }

    private boolean validChecksum(Map<String, String> payload) throws UnsupportedEncodingException {
        String vnp_SecureHash = payload.get("vnp_SecureHash");
        payload.remove("vnp_SecureHashType");
        payload.remove("vnp_SecureHash");

        VNPayEnv env = vnpProperties.getVNPayEnv();
        String vnp_HashSecret = env.getVnpHashSecret();
        String signValue = CommonUtils.hashAllFields(payload, vnp_HashSecret);

        return signValue.equals(vnp_SecureHash);
    }

    private void savePaymentInfo(PaymentVNP paymentVNP, Map<String, String> payload, String ipnResCode) {
        paymentVNP.setVnpBankCode(payload.get("vnp_BankCode"));
        paymentVNP.setVnpBankTranNo(payload.get("vnp_BankTranNo"));
        paymentVNP.setVnpCardType(payload.get("vnp_CardType"));
        paymentVNP.setVnpPayDate(CommonUtils.StringToDate(payload.get("vnp_PayDate"), "yyyyMMddHHmmss"));
        paymentVNP.setVnpResponseCode(payload.get("vnp_ResponseCode"));
        paymentVNP.setVnpTransactionNo(payload.get("vnp_TransactionNo"));

        if ("00".equals(ipnResCode)) {
            paymentVNP.getPayment().setStatus(PaymentStatus.PAYMENT_SUCCESS);
        } else {
            paymentVNP.getPayment().setStatus(PaymentStatus.PAYMENT_FAILED);
        }

        vnpPaymentRepository.save(paymentVNP);
    }

}
