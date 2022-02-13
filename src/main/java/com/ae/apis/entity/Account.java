package com.ae.apis.entity;


import com.ae.apis.entity.enums.Gender;
import com.ae.apis.entity.enums.LoginStatus;
import com.ae.apis.entity.enums.RegisterStatus;
import com.ae.apis.entity.enums.UserRole;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "account")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "email", unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "login_status")
    private LoginStatus loginStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "register_status")
    private RegisterStatus registerStatus;
    @Column(name = "registration_token")
    private String registrationToken;
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    public String getFullName() {
        if(!StringUtils.isEmpty(lastName) && !StringUtils.isEmpty(firstName)) {
            return lastName.concat(" ").concat(firstName).trim();
        } else if(!StringUtils.isEmpty(lastName)){
            return lastName;
        } else if(!StringUtils.isEmpty(firstName)){
            return firstName;
        }
        return null;


    }
}
