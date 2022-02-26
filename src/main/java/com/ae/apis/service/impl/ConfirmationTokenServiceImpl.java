package com.ae.apis.service.impl;

import com.ae.apis.controller.dto.SMSVerifyType;
import com.ae.apis.entity.Account;
import com.ae.apis.entity.ConfirmationToken;
import com.ae.apis.repository.ConfirmationTokenRepository;
import com.ae.apis.service.CommunicationService;
import com.ae.apis.service.ConfirmationTokenService;
import com.ae.apis.service.external.sms.common.SMSProperties;
import com.ae.apis.utils.AppUtils;
import com.ae.apis.utils.AsyncTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmationTokenService.class);

    @Autowired
    private SMSProperties properties;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private CommunicationService communicationService;

    @Override
    public ConfirmationToken generateConfirmationToken(Account account, String code, SMSVerifyType type, String deviceType) {
        List<ConfirmationToken> existingTokens = confirmationTokenRepository.findByUserIdAndType(account.getId(), type.name());
        if (!CollectionUtils.isEmpty(existingTokens)) {
            confirmationTokenRepository.deleteAll(existingTokens);
        }

        OffsetDateTime expireTime = OffsetDateTime.now().plus(properties.getAuth().getExpire(), ChronoUnit.MILLIS);
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmationToken(code);
        confirmationToken.setExpireDate(new Date(expireTime.toInstant().toEpochMilli()));
        confirmationToken.setUserId(account.getId());
        confirmationToken.setType(type.name());
        ConfirmationToken persisted = confirmationTokenRepository.save(confirmationToken);

        // log client address
        try {
            HttpServletRequest request = AppUtils.getCurrentHttpRequest().get();
            logger.info(String.format(
                    "SMS send request from IP: %s, phoneNumber: %s, type: %s",
                    request.getRemoteAddr(), account.getPhoneNumber(), type
            ));

            Enumeration<String> headerNames = AppUtils.getCurrentHttpRequest().get().getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String nextE = headerNames.nextElement();
                logger.info(String.format("Header name: %s, value: %s", nextE, request.getHeader(nextE)));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        AsyncTaskExecutor.execute(() -> sendOTP(account.getPhoneNumber(), code, type.name(), deviceType));

        return persisted;
    }

    @Override
    public void sendOTP(String phoneNumber, String code, String type, String deviceType) {
        String message = generateSMS(type, deviceType, phoneNumber, code);
        communicationService.sendSMS(phoneNumber, message);
    }

    @Override
    public boolean confirmationTokenVerified(Long userId, String code, SMSVerifyType type) {
        Optional<ConfirmationToken> confirmationTokenOpt = confirmationTokenRepository.findValidConfirmationTokens(
                        userId, code, type.name(), new Date()
                ).stream()
                .findFirst();

        if (confirmationTokenOpt.isEmpty()) {
            return false;
        }

        confirmationTokenRepository.delete(confirmationTokenOpt.get());
        return true;
    }

    @Override
    public void deleteTokenById(Long id) {
        confirmationTokenRepository.deleteById(id);
    }

    @Override
    public ConfirmationToken findByUserId(Long userId) {
        return confirmationTokenRepository.findByUserId(userId).stream().findFirst().orElse(null);
    }

    private String generateSMS(String type, String deviceType, String phoneNumber, String code) {
        switch (type) {
            case "ACTIVE_ACCOUNT":
                if ("android".equalsIgnoreCase(deviceType)) {
                    if (AppUtils.isVietNamMobileNetworkPhoneNumber(phoneNumber)) {
                        return String.format("Nhap ma xac thuc va mat khau %s de dang nhap ung dung. ", code);
                    }
                    return String.format("Nhập mã xác thực và mật khẩu %s để đăng nhập ứng dụng. ", code);
                }
                if (AppUtils.isVietNamMobileNetworkPhoneNumber(phoneNumber)) {
                    return "Ma xac thuc va mat khau cua ban la: " + code;
                }
                return "Mã xác thực và mật khẩu của bạn là: " + code;
            case "LOGIN":
                if ("android".equalsIgnoreCase(deviceType)) {
                    if (AppUtils.isVietNamMobileNetworkPhoneNumber(phoneNumber)) {
                        return String.format("Nhap ma xac thuc %s de dang nhap ung dung. ", code);
                    }
                    return String.format("Nhập mã xác thực %s để đăng nhập ứng dụng. ", code);
                }
                if (AppUtils.isVietNamMobileNetworkPhoneNumber(phoneNumber)) {
                    return "Ma dang nhap cua ban la: " + code;
                }
                return "Mã đăng nhập của bạn là: " + code;
            default:
                throw new RuntimeException("OTP type is invalid");
        }
    }
}
