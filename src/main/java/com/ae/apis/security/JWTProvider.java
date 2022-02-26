package com.ae.apis.security;

import com.ae.apis.security.common.SecurityJwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class JWTProvider {

    @Autowired
    private SecurityJwtProperties properties;

    public String generate(CustomUserDetails user) {
        return JWT.create()
                .withSubject(
                        format(
                                "%s,%s,%s,%s",
                                user.getId(),
                                user.getPhoneNumber(),
                                user.getEmail(),
                                user.getName()
                        )
                )
                .withExpiresAt(new Date(System.currentTimeMillis() + properties.getExpire()))
                .sign(Algorithm.HMAC512(properties.getSecret().getBytes()));
    }

    public String verify(String token) {
        return JWT.require(
                        Algorithm.HMAC512(
                                properties.getSecret().getBytes()
                        )
                )
                .build()
                .verify(token)
                .getSubject();
    }

    public String getUsername(String token) {
        String userInfo = verify(token);
        return StringUtils.isEmpty(userInfo) ? null : userInfo.split(",")[1];
    }

    public boolean validate(String token) {
        return !StringUtils.isEmpty(verify(token));
    }
}
