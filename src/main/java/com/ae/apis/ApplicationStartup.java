package com.ae.apis;


import com.ae.apis.entity.Account;
import com.ae.apis.entity.enums.Gender;
import com.ae.apis.entity.enums.LoginStatus;
import com.ae.apis.entity.enums.RegisterStatus;
import com.ae.apis.entity.enums.UserRole;
import com.ae.apis.repository.AccountRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public ApplicationStartup(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //dummy account user
            Account user = new Account();
            user.setEmail("user@gmail.com");
            user.setPhoneNumber("0335597033");
            user.setFirstName("Nguyen");
            user.setLastName("A");
            user.setPassword(passwordEncoder.encode("111111"));
            user.setLoginStatus(LoginStatus.LOGOUT);
            user.setRegisterStatus(RegisterStatus.COMPLETED);
            user.setUserRole(UserRole.ROLE_USER);
            user.setGender(Gender.MALE);
            accountRepository.saveAndFlush(user);

            //dummy account user
            Account admin = new Account();
            admin.setEmail("aadmin@gamil.com");
            admin.setPhoneNumber("033999898");
            admin.setFirstName("Nguyen");
            admin.setLastName("B");
            admin.setPassword(passwordEncoder.encode("111111"));
            admin.setLoginStatus(LoginStatus.LOGOUT);
            admin.setRegisterStatus(RegisterStatus.COMPLETED);
            admin.setUserRole(UserRole.ROLE_ADMIN);
            admin.setGender(Gender.MALE);
            accountRepository.saveAndFlush(admin);
        } catch (Exception e) {
        }
    }
}
