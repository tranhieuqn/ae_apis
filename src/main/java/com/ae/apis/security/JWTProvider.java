package com.ae.apis.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class JWTProvider {
    
    @Value("${security.jwt.secret}")
    private String SECRET;

    @Value("${security.jwt.expire}")
    private Long EXPIRE;

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
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    public String verify(String token) {
        return JWT.require(
                        Algorithm.HMAC512(
                                SECRET.getBytes()
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
