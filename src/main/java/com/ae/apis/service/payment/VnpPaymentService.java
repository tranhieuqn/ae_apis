package com.ae.apis.service.payment;


import com.ae.apis.service.payment.dto.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface VnpPaymentService {
    VNPPaymentResponse createPayment(VNPPaymentRequest payload) throws UnsupportedEncodingException;
    PaymentIPNProcessResponse paymentIPNProcess(Map<String,String> payload);
    CheckPaymentProcessResponse checkPaymentProcess(Map<String, String> payload);
    String doRefund(VNPayRefundRequest payload) throws Exception;
}
