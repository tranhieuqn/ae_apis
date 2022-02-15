package com.ae.apis.service.payment.dto;

import com.ae.apis.entity.Payment;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class VNPPaymentRequest {
    private Payment payment;

    private String ipClient;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String vnpTxnRef;

    private String orderType;

    private String orderInfo;

    private Long accountId;
}
