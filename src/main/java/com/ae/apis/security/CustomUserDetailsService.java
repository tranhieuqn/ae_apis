package com.ae.apis.security;


import com.ae.apis.entity.Account;
import com.ae.apis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account user = accountService.findByEmail(username);

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().toString());

        return new CustomUserDetails(
                user.getId(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
