package com.ae.apis.service.payment.dto;

import com.ae.apis.entity.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNPayRefundRequest implements RefundRequest{
    private Payment payment;
    private String env;
    private Long amount;
    private String vnpTxnRef;
    private String vnpTxnDateStr;
}
