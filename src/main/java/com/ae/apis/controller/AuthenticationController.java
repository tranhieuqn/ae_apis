package com.ae.apis.controller;


import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.EmptyResponse;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.controller.dto.LoginRequest;
import com.ae.apis.controller.dto.LoginResponse;
import com.ae.apis.controller.dto.UserAccountVerifyRequest;
import com.ae.apis.controller.dto.UserRegisterRequest;
import com.ae.apis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public RestResponse<?> login(@Valid @RequestBody LoginRequest request) {
        accountService.login(request);
        Map<String, String> result = new HashMap<>();
        result.put("sendSMSVerifyCode", "success");

        return ResponseBuilder.build(result);
    }

    @PostMapping("/register")
    public RestResponse<?> register(@Valid @RequestBody UserRegisterRequest request) {
        accountService.register(request);
        Map<String, String> result = new HashMap<>();
        result.put("sendSMSVerifyCode", "success");

        return ResponseBuilder.build(result);
    }

    @PostMapping("/verify-account")
    public RestResponse<LoginResponse> verifyAccount(@Valid @RequestBody UserAccountVerifyRequest request) {
        LoginResponse loginResponse = accountService.verifyAccount(request);

        return ResponseBuilder.build(loginResponse);
    }

    @PostMapping("/logout")
    public RestResponse<?> logout() {
        accountService.logout();

        return EmptyResponse.instance;
    }
}
