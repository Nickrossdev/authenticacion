package com.nickrossdev.authentication.utils;

import com.nickrossdev.authentication.domain.authentication.models.Account;
import com.nickrossdev.authentication.exceptions.UnauthenticatedAccountException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static Account getAuthenticatedAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Account account) {
            return account;
        }
        throw new UnauthenticatedAccountException();
    }
}
