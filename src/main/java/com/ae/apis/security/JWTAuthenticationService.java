package com.ae.apis.security;


import com.ae.apis.controller.dto.LoginResponse;
import com.ae.apis.controller.dto.UserAccountVerifyRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.ae.apis.common.Constants.TOKEN_PREFIX;


@Component
public class JWTAuthenticationService {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse authenticate(UserAccountVerifyRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getVerifyCode()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
        String token = jwtProvider.generate(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(String.format("%s%s", TOKEN_PREFIX, token));
        AccountLoginResponse accInfo = new AccountLoginResponse();
        BeanUtils.copyProperties(userDetails, accInfo);
        loginResponse.setAccountInfo(accInfo);

        return loginResponse;
    }
}
