package com.ae.apis.service.payment.impl;

import com.ae.apis.entity.Payment;
import com.ae.apis.entity.enums.PaymentStatus;
import com.ae.apis.entity.enums.PaymentType;
import com.ae.apis.repository.PaymentRepository;
import com.ae.apis.service.payment.PaymentCreator;
import com.ae.apis.service.payment.VnpPaymentService;
import com.ae.apis.service.payment.dto.PaymentCreatedRes;
import com.ae.apis.service.payment.dto.RefundPaymentRes;
import com.ae.apis.service.payment.dto.RefundRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CODPaymentCreator extends PaymentCreator {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VnpPaymentService vnpPaymentService;

    @Override
    public PaymentCreatedRes create(Long userId, Long fee, Long originalFee, String code, String type) {
        Payment payment = new Payment();
        payment.setFee(fee);
        payment.setOriginalFee(originalFee);
        payment.setType(PaymentType.COD);
        payment.setStatus(PaymentStatus.NOT_PAYMENT_YET);
        payment.setTransactionId(code);
        payment.setEnv("COD");
        payment = paymentRepository.save(payment);

        return new PaymentCreatedRes(payment, null);
    }

    @Override
    public RefundPaymentRes doRefund(RefundRequest payload) {
        return null;
    }

}
