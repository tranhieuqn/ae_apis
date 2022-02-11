package com.ae.apis.controller;


import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.controller.dto.LoginRequest;
import com.ae.apis.controller.dto.LoginResponse;
import com.ae.apis.security.JWTAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private JWTAuthenticationService authenticationService;

    @PostMapping("/login")
    public RestResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authenticationService.authenticate(request);
        return ResponseBuilder.build(loginResponse);
    }

    //TODO: Register
}
