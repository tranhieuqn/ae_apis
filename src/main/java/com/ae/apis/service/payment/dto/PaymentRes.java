package com.ae.apis.service.payment.dto;

import com.ae.apis.entity.enums.PaymentStatus;
import com.ae.apis.entity.enums.PaymentType;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentRes {
	private Long id;
	private PaymentType type;
	private PaymentStatus status;
	private Date createdTime;
	private String transactionId;
	private Long fee;
	private Long originalFee;
	private Long userId;
	private String userName;
	private String userPhoneNumber;
	private Long familyId;
	private String familyName;
	private Long careProviderId;
	private String careProviderName;
	private Long doctorId;
	private String doctorName;
	private Long bookingId;
//	private BookingType bookingType;
	private Date bookingCreatedTime;
//	private BookingStatus bookingStatus;
	private Long labTestPackageId;
	private String labTestPackageName;
}