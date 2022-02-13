package com.ae.apis.service;

import com.ae.apis.controller.dto.LoginRequest;
import com.ae.apis.controller.dto.LoginResponse;
import com.ae.apis.controller.dto.UserAccountVerifyRequest;
import com.ae.apis.controller.dto.UserRegisterRequest;
import com.ae.apis.entity.Account;

public interface AccountService {
    Account findByEmail(String email);
    Account findById(Long id);
    Account findByPhoneNumber(String username);
    Long register(UserRegisterRequest request);
    LoginResponse verifyAccount(UserAccountVerifyRequest request);
    LoginResponse authenticate(UserAccountVerifyRequest request);
    Long login(LoginRequest request);
    void logout();
}
