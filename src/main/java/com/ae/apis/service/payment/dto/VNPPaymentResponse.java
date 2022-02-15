package com.ae.apis.service.payment.dto;

import com.ae.apis.entity.PaymentVNP;
import lombok.Data;

@Data
public class VNPPaymentResponse {
    private PaymentVNP paymentInfo;
    private String paymentUrl;
}
