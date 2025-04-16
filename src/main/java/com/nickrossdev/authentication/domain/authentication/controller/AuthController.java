package com.nickrossdev.authentication.domain.authentication.controller;

import com.nickrossdev.authentication.api.ApiResponse;
import com.nickrossdev.authentication.domain.authentication.dto.LoginAccountRequest;
import com.nickrossdev.authentication.domain.authentication.dto.RegisterAccountRequest;
import com.nickrossdev.authentication.domain.authentication.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerNewAccount(@RequestBody @Valid RegisterAccountRequest request) {
        authService.registerNewAccount(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(), true,"Cuenta creada correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginAccount(HttpServletResponse response, @RequestBody @Valid LoginAccountRequest request) {
        authService.loginAccount(response, request);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true,"Login correcto"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logoutAccount(HttpServletResponse response) {
        authService.logoutAccount(response);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(),true,"Logout correcto"));
    }

}
