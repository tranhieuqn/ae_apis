package com.ae.apis.service.payment;


import com.ae.apis.entity.enums.PaymentType;
import com.ae.apis.service.payment.dto.PaymentCreatedRes;
import com.ae.apis.service.payment.dto.RefundPaymentRes;
import com.ae.apis.service.payment.dto.RefundRequest;

public interface PaymentService {
    PaymentCreatedRes createPayment(PaymentType type, Long userId, Long fee, Long originalFee, String code, String vnpStrType);
    RefundPaymentRes doRefund(PaymentType type, RefundRequest payload) throws Exception;
}