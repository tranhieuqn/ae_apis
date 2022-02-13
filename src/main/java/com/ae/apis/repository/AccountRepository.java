package com.ae.apis.repository;

import com.ae.apis.entity.Account;
import com.ae.apis.entity.enums.LoginStatus;
import com.ae.apis.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhoneNumberAndUserRole(String phoneNumber, UserRole roleUser);

    Optional<Account> findByPhoneNumber(String phoneNumber);

    Optional<Account> findByPhoneNumberAndLoginStatus(String phoneNumber, LoginStatus login);
}
