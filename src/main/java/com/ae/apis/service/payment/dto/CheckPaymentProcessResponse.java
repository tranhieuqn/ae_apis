package com.ae.apis.service.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class CheckPaymentProcessResponse {
    private String code;
    private String message;
    private PaymentProcessDataResponse data;

    public static CheckPaymentProcessResponse ofInvalidChecksum() {
        CheckPaymentProcessResponse response = new CheckPaymentProcessResponse();
        response.setCode("97");
        response.setMessage("Chữ ký không hợp lệ.");

        return response;
    }

    public static CheckPaymentProcessResponse ofPaymentFailed() {
        CheckPaymentProcessResponse response = new CheckPaymentProcessResponse();
        response.setCode("99");
        response.setMessage("Giao dịch thất bại.");

        return response;
    }

    @Getter
    @Setter
    public static class PaymentProcessDataResponse {
        private String transactionNo;
        private BigDecimal amount;
        private Date payDate;
    }
}
