package com.nickrossdev.authentication.domain.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginAccountRequest(
        @NotBlank(message = "El correo es obligatorio.")
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}
