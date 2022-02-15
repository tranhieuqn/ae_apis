package com.ae.apis.service.payment.dto;


import com.ae.apis.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreatedRes {
	private Payment payment;
	private String paymentUrl;
}