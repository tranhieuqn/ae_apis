package com.ae.apis.service.payment.impl;

import com.ae.apis.entity.enums.PaymentType;
import com.ae.apis.service.payment.PaymentCreator;
import com.ae.apis.service.payment.PaymentService;
import com.ae.apis.service.payment.dto.PaymentCreatedRes;
import com.ae.apis.service.payment.dto.RefundPaymentRes;
import com.ae.apis.service.payment.dto.RefundRequest;
import com.ae.apis.service.payment.error.PaymentTypeNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional
    public PaymentCreatedRes createPayment(PaymentType type, Long userId, Long fee, Long originalFee, String code, String vnpStrType) {
        PaymentCreator paymentCreator;
        switch (type) {
            case VNPAY:
                paymentCreator = applicationContext.getBean(VNPAYPaymentCreator.class);
                break;
            case COD:
                paymentCreator = applicationContext.getBean(CODPaymentCreator.class);
                break;
            default:
                throw new PaymentTypeNotValidException();
        }
        return paymentCreator.create(userId, fee, originalFee, code, vnpStrType);
    }

    @Override
    public RefundPaymentRes doRefund(PaymentType type, RefundRequest payload) throws Exception {
        PaymentCreator paymentCreator;
        switch (type) {
            case VNPAY:
                paymentCreator = applicationContext.getBean(VNPAYPaymentCreator.class);
                break;
            default:
                throw new PaymentTypeNotValidException();
        }

        return paymentCreator.doRefund(payload);
    }

}
