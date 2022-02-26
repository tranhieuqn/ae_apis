package com.ae.apis.service.payment;


import com.ae.apis.service.payment.dto.PaymentCreatedRes;
import com.ae.apis.service.payment.dto.RefundPaymentRes;
import com.ae.apis.service.payment.dto.RefundRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PaymentCreator {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract PaymentCreatedRes create(Long userId, Long fee, Long originalFee, String code, String strType);

    public RefundPaymentRes doRefund(RefundRequest payload) {
        return null;
    }
}