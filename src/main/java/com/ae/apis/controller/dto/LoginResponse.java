package com.ae.apis.controller.dto;


import com.ae.apis.security.AccountLoginResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private AccountLoginResponse accountInfo;
}
