package com.ae.apis.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails extends User {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;

    public CustomUserDetails(Long id,
                             String name,
                             String phoneNumber,
                             String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(phoneNumber, password, authorities);

        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
