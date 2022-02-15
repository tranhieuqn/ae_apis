package com.ae.apis.service.payment.dto;

import com.ae.apis.entity.enums.PaymentStatus;
import com.ae.apis.entity.enums.PaymentType;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PaymentDetailRes {
	private Long id;
	private Long fee;
	private Long originalFee;
	private PaymentType type;
	private PaymentStatus status;
	private Date createdTime;
	private String transactionId;
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
	private Long promotionId;
	private String promotionCode;
	private String symptom;
	private String diagnosis;
	private Set<String> labTestPDFs;
	private Set<String> prescriptionPDFs;
	private Set<String> labTestPackagePDFs;
	private Long labTestPackageId;
	private String labTestPackageName;
	private int totalCallDuration;
	private int totalInCallDuration;
	private long totalCallCount;
}
