package com.nickrossdev.authentication.services;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Value("${jwt.token.name}")
    private String tokenName;

    @Value("${jwt.token.expiration}")
    private Integer jwtTokenExpiration;

    public Cookie createCookie() {
        Cookie cookie = new Cookie(tokenName, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    public Cookie createCookie(String token) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtTokenExpiration / 1000);
        return cookie;
    }
}
