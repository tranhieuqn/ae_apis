package com.ae.apis.config.error;

import org.springframework.security.core.AuthenticationException;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
