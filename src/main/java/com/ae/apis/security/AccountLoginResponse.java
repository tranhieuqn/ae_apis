package com.ae.apis.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class AccountLoginResponse {
    private String name;
    private String phoneNumber;
    private String email;
}
