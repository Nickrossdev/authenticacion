package com.nickrossdev.authentication.filter;

import com.nickrossdev.authentication.domain.authentication.models.Account;
import com.nickrossdev.authentication.domain.authentication.models.AccountRole;
import com.nickrossdev.authentication.domain.authentication.repositories.AccountRepository;
import com.nickrossdev.authentication.domain.authentication.repositories.AccountRoleRepository;
import com.nickrossdev.authentication.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    @Value("${jwt.token.name}")
    private String tokenName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(tokenName)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null) {
            try {
                UUID idAccount = UUID.fromString(jwtUtil.getIdAccount(token));
                Account account = accountRepository.findById(idAccount).orElseThrow();

                List<AccountRole> accountRoles = accountRoleRepository.findByAccount(account);
                List<SimpleGrantedAuthority> authorities = accountRoles.stream()
                        .filter(AccountRole::getActive)
                        .map(accountRole -> new SimpleGrantedAuthority("ROLE_" + accountRole.getRole().getName()))
                        .toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(account,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized: Invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
