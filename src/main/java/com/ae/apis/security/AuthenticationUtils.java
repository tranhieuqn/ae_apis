package com.ae.apis.security;

import com.ae.apis.entity.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static CustomUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails;
    }

    public static Long getUserId(){
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getId();
    }

    public static String getUserEmail(){
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getEmail();
    }

    public static boolean isRoleUser() {
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(
                        x -> UserRole.ROLE_USER.name().equals(x.getAuthority())
                );
    }

    public static boolean isRoleAdmin() {
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(
                        x -> UserRole.ROLE_ADMIN.name().equals(x.getAuthority())
                );
    }
}
