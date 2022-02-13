package com.ae.apis.security;


import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.entity.Account;
import com.ae.apis.entity.enums.LoginStatus;
import com.ae.apis.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account user = accountRepository.findByPhoneNumberAndLoginStatus(username, LoginStatus.LOGIN).orElseThrow(
                () -> new NotFoundException(Account.class, username)
        );

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().toString());

        return new CustomUserDetails(
                user.getId(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                Arrays.asList(authority)
        );
    }
}
