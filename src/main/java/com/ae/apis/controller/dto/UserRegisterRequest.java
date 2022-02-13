package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.Gender;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterRequest {
    @NotBlank
    private String phoneNumber;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String deviceType;
}
