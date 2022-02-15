package com.ae.apis.service.payment.dto;


import com.ae.apis.entity.Payment;
import com.ae.apis.entity.enums.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundPaymentRes {
	private RefundStatus status;
	private String reason;
}