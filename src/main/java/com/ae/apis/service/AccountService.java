package com.ae.apis.service;

import com.ae.apis.entity.Account;

public interface AccountService {
    Account findByEmail(String email);
}
