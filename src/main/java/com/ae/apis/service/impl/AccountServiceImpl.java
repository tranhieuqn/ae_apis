package com.ae.apis.service.impl;

import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.entity.Account;
import com.ae.apis.repository.AccountRepository;
import com.ae.apis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public Account findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(Account.class, email)
        );
    }
}
