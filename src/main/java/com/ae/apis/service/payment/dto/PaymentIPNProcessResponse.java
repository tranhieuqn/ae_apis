package com.ae.apis.service.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentIPNProcessResponse {
    @JsonProperty("RspCode")
    private String rspCode;

    @JsonProperty("Message")
    private String message;

    public static PaymentIPNProcessResponse ofInvalidChecksum() {
        PaymentIPNProcessResponse response = new PaymentIPNProcessResponse();
        response.setRspCode("97");
        response.setMessage("Invalid checksum.");

        return response;
    }

    public static PaymentIPNProcessResponse ofOrderNotFound() {
        PaymentIPNProcessResponse response = new PaymentIPNProcessResponse();
        response.setRspCode("01");
        response.setMessage("Order not found.");

        return response;
    }

    public static PaymentIPNProcessResponse ofOrderAlreadyConfirmed() {
        PaymentIPNProcessResponse response = new PaymentIPNProcessResponse();
        response.setRspCode("02");
        response.setMessage("Order already confirmed.");

        return response;
    }

    public static PaymentIPNProcessResponse ofInvalidAmount() {
        PaymentIPNProcessResponse response = new PaymentIPNProcessResponse();
        response.setRspCode("04");
        response.setMessage("Invalid amount.");

        return response;
    }

    public static PaymentIPNProcessResponse ofOrderConfirmSuccess() {
        PaymentIPNProcessResponse response = new PaymentIPNProcessResponse();
        response.setRspCode("00");
        response.setMessage("Confirm success.");

        return response;
    }

    public static PaymentIPNProcessResponse ofUnknownError() {
        PaymentIPNProcessResponse response = new PaymentIPNProcessResponse();
        response.setRspCode("99");
        response.setMessage("Unknown error.");

        return response;
    }
}
