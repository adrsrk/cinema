package com.app.model.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Введите вашу электронную почту")
        String email,
        @NotBlank(message = "Введите ваш пароль!")
        String password) {
}
