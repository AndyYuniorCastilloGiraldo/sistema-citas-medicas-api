package com.andycg.citas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "El nombre de usuario no puede estar vacío") @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres") String username,

        @NotBlank(message = "La contraseña no puede estar vacía") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
}
