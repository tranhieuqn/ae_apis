package com.ae.apis.service.payment;


import com.ae.apis.service.payment.dto.PaymentCreatedRes;
import com.ae.apis.service.payment.dto.RefundPaymentRes;
import com.ae.apis.service.payment.dto.RefundRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PaymentCreator {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentCreator.class);

    public abstract PaymentCreatedRes create(Long userId, Long fee, Long originalFee, String code, String strType);

    public abstract RefundPaymentRes doRefund(RefundRequest payload) throws Exception;
}