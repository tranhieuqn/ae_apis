package com.ae.apis.config.error;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

