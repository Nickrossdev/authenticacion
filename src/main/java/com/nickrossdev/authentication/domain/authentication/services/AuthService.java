package com.nickrossdev.authentication.domain.authentication.services;

import com.nickrossdev.authentication.domain.authentication.dto.LoginAccountRequest;
import com.nickrossdev.authentication.domain.authentication.dto.RegisterAccountRequest;
import com.nickrossdev.authentication.domain.authentication.models.Account;
import com.nickrossdev.authentication.domain.authentication.models.AccountRole;
import com.nickrossdev.authentication.domain.authentication.models.Role;
import com.nickrossdev.authentication.services.CookieService;
import com.nickrossdev.authentication.services.TokenService;
import com.nickrossdev.authentication.utils.AuthUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountRoleService accountRoleService;
    private final TokenService tokenService;
    private final CookieService cookieService;

    @Value("${role.default}")
    private String roleDefault;

    @Transactional
    public void registerNewAccount(RegisterAccountRequest request) {
        Role role = roleService.getRoleByName(roleDefault);
        Account account = accountService.createNewAccount(request);
        AccountRole accountRole = new AccountRole();

        accountRole.setAccount(account);
        accountRole.setRole(role);
        accountRole.setActive(true);

        accountRoleService.registerNewAccountRole(accountRole);
    }

    public void loginAccount(HttpServletResponse response, LoginAccountRequest request) {
        Account account = accountService.getAccountByEmail(request.email());
        accountService.verifyPassword(request.password(),account.getPassword());
        String token = tokenService.generateToken(account.getId());

        accountService.saveToken(account,token);

        Cookie cookie = cookieService.createCookie(token);
        response.addCookie(cookie);
    }

    public void logoutAccount(HttpServletResponse response) {
        Account account = AuthUtils.getAuthenticatedAccount();
        accountService.deleteToken(account);
        Cookie cookie = cookieService.createCookie();
        response.addCookie(cookie);
    }
}
