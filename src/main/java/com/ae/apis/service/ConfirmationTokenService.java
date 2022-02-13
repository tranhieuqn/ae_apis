package com.ae.apis.service;


import com.ae.apis.controller.dto.SMSVerifyType;
import com.ae.apis.entity.Account;
import com.ae.apis.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken generateConfirmationToken(Account account, String code, SMSVerifyType type, String deviceType);

    boolean confirmationTokenVerified(Long userId, String code, SMSVerifyType type);

    void sendOTP(String phoneNumber, String code, String type, String deviceType);

    ConfirmationToken findByUserId(Long userId);

    void deleteTokenById(Long id);
}