package com.ae.apis.service.payment.impl;

import com.ae.apis.entity.Payment;
import com.ae.apis.entity.enums.PaymentStatus;
import com.ae.apis.entity.enums.PaymentType;
import com.ae.apis.entity.enums.RefundStatus;
import com.ae.apis.repository.PaymentRepository;
import com.ae.apis.service.payment.PaymentCreator;
import com.ae.apis.service.payment.VnpPaymentService;
import com.ae.apis.service.payment.common.CommonUtils;
import com.ae.apis.service.payment.common.VNPayProperties;
import com.ae.apis.service.payment.dto.*;
import com.ae.apis.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ae.apis.entity.enums.RefundStatus.REFUND_FAILED;
import static com.ae.apis.entity.enums.RefundStatus.REFUND_SUCCESS;


@Component
public class VNPAYPaymentCreator extends PaymentCreator {

    @Autowired
    private VNPayProperties properties;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VnpPaymentService vnpPaymentService;

    @Override
    public PaymentCreatedRes create(Long userId, Long fee, Long originalFee, String code, String type) {
        Payment payment = new Payment();
        payment.setFee(fee);
        payment.setOriginalFee(originalFee);
        payment.setType(PaymentType.VNPAY);
        payment.setStatus(PaymentStatus.NOT_PAYMENT_YET);
        payment.setTransactionId(code);
        payment.setEnv(properties.getEnv());
        payment = paymentRepository.save(payment);

        VNPPaymentRequest paymentRequest = new VNPPaymentRequest();
        paymentRequest.setPayment(payment);
        paymentRequest.setIpClient(CommonUtils.getIpAddress(AppUtils.getCurrentHttpRequest().get()));
        paymentRequest.setAmount(new BigDecimal(fee));
        paymentRequest.setVnpTxnRef(CommonUtils.generateVnpTxnRef());
        paymentRequest.setOrderType(type);
        paymentRequest.setOrderInfo(String.valueOf(payment.getId()));
        paymentRequest.setAccountId(userId);

        VNPPaymentResponse paymentResult = null;
        try {
            paymentResult = vnpPaymentService.createPayment(paymentRequest);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return new PaymentCreatedRes(payment, paymentResult == null ? null : paymentResult.getPaymentUrl());
    }

    @Override
    public RefundPaymentRes doRefund(RefundRequest payload) {
        RefundStatus status = REFUND_FAILED;
        String reason = Strings.EMPTY;
        try {
            String response = vnpPaymentService.doRefund((VNPayRefundRequest) payload);
            Map<String, String> responseMap = Arrays.stream(response.split("&"))
                    .map(s -> s.split("="))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));

            if ("00".equals(responseMap.get("vnp_ResponseCode"))) {
                status = REFUND_SUCCESS;
            }
            reason = (new ObjectMapper()).writeValueAsString(responseMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
            reason = e.getMessage();
            status = REFUND_FAILED;
        }

        return new RefundPaymentRes(status, reason);
    }

}
