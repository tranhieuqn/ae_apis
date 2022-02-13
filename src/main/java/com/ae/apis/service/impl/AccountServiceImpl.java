package com.ae.apis.service.impl;

import com.ae.apis.config.error.BusinessException;
import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.controller.dto.*;
import com.ae.apis.entity.Account;
import com.ae.apis.entity.enums.LoginStatus;
import com.ae.apis.entity.enums.RegisterStatus;
import com.ae.apis.entity.enums.UserRole;
import com.ae.apis.repository.AccountRepository;
import com.ae.apis.security.AuthenticationUtils;
import com.ae.apis.security.JWTAuthenticationService;
import com.ae.apis.service.AccountService;
import com.ae.apis.service.ConfirmationTokenService;
import com.ae.apis.utils.RandomCodeGenerator;
import com.ae.apis.utils.SMSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RandomCodeGenerator randomCodeGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JWTAuthenticationService authenticationService;

    @Autowired
    private SMSProperties properties;

    @Override
    public Account findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(Account.class, email)
        );
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(Account.class, id)
        );
    }

    @Override
    public Account findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new NotFoundException(Account.class, phoneNumber)
        );
    }

    @Override
    public Long register(UserRegisterRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String verifyCode;
        if (phoneNumber.contains(properties.getMockPhoneNumber())) {
            verifyCode = "9430";
        } else {
            verifyCode = randomCodeGenerator.generateCode(4, 0);
        }

        Account user = repository.findByPhoneNumberAndUserRole(phoneNumber, UserRole.ROLE_USER)
                .orElseGet(
                        () -> {
                            Account newUser = new Account();
                            newUser.setUserRole(UserRole.ROLE_USER);
                            newUser.setRegisterStatus(RegisterStatus.AWAIT_VERIFY_PHONE);
                            newUser.setPhoneNumber(phoneNumber);
                            newUser.setFirstName(request.getFirstName());
                            newUser.setLastName(request.getLastName());
                            newUser.setGender(request.getGender());

                            return newUser;
                        }
                );
        user.setPassword(passwordEncoder.encode(verifyCode));
        user = repository.save(user);

        confirmationTokenService.generateConfirmationToken(
                user, verifyCode, SMSVerifyType.ACTIVE_ACCOUNT, request.getDeviceType()
        );

        return user.getId();
    }

    @Override
    public LoginResponse verifyAccount(UserAccountVerifyRequest request) {

        Optional<Account> userOpt = repository.findByPhoneNumberAndUserRole(request.getPhoneNumber(), UserRole.ROLE_USER);
        if (userOpt.isEmpty()) {
            throw new NotFoundException(Account.class, request.getPhoneNumber());
        }
        Account user = userOpt.get();

        if (!confirmationTokenService.confirmationTokenVerified(user.getId(), request.getVerifyCode(), request.getVerifyType())) {
            throw new BusinessException("Confirmation token not found or expired");
        }
        user.setLoginStatus(LoginStatus.LOGIN);
        repository.save(user);

        return authenticate(request);
    }


    @Override
    public LoginResponse authenticate(UserAccountVerifyRequest request) {
        return authenticationService.authenticate(request);
    }

    @Override
    public Long login(LoginRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String verifyCode;
        if (phoneNumber.contains(properties.getMockPhoneNumber())) {
            verifyCode = "9430";
        } else {
            verifyCode = randomCodeGenerator.generateCode(4, 0);
        }

        Account user = repository.findByPhoneNumberAndUserRole(phoneNumber, UserRole.ROLE_USER).orElseThrow(
                () -> new NotFoundException(Account.class, request.getPhoneNumber())
        );

        user.setPassword(passwordEncoder.encode(verifyCode));
        user = repository.save(user);

        confirmationTokenService.generateConfirmationToken(
                user, verifyCode, SMSVerifyType.LOGIN, request.getDeviceType()
        );

        return user.getId();
    }

    @Override
    public void logout() {
        Long userId = AuthenticationUtils.getUserId();
        Account account = findById(userId);
        account.setLoginStatus(LoginStatus.LOGOUT);
        repository.save(account);
    }

}
