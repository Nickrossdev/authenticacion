package com.nickrossdev.authentication.domain.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterAccountRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String username,
        @NotBlank(message = "El correo electrónico es obligatorio.")
        String email,
        @NotBlank(message = "La contraseña es obligatoria.")
        String password
) {
}
