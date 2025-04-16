package com.nickrossdev.authentication.services;

import com.nickrossdev.authentication.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;

    public String generateToken(UUID id) {
        String token = jwtUtil.generateToken(id);
        return token;
    }
}
