package com.ae.apis.controller.dto;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserAccountVerifyRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String verifyCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SMSVerifyType verifyType;
}
