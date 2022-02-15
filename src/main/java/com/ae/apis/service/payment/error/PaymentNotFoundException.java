package com.ae.apis.service.payment.error;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {
        super("Payment not found exception");
    }
}
