package com.ae.apis.service.payment.error;

public class PaymentTypeNotValidException extends RuntimeException {
    public PaymentTypeNotValidException() {
        super("Payment type not valid exception");
    }
}
