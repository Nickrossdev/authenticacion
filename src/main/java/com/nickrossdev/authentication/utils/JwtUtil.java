package com.nickrossdev.authentication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${SECRET_KEY}")
    private String secretKey;
    @Value("${jwt.token.expiration}")
    private Integer jwtTokenExpiration;

    public String generateToken(UUID id) {
        String token =  JWT.create()
                .withSubject(id.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtTokenExpiration))
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }

    public String getIdAccount(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getSubject();
    }
}
