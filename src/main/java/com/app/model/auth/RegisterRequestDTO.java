package com.app.model.auth;

import com.app.entity.enums.Role;
import jakarta.validation.constraints.*;

public record RegisterRequestDTO(
        @NotBlank(message = "Введите вашу электронную почту!")
        @Email(message = "Неверный формат электроной почты!")
        String email,

        @NotBlank(message = "Введите ваш пароль!")
        @Size(min = 8, max = 12, message = "Пароль должен содержать от 8 до 12 символов!")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Пароль должен содержать хотя бы одну строчную букву, одну заглавную букву, одну цифру и один спецсимвол (@$!%*?&)."
        )
        String password,

        @NotNull(message = "Выберите роль!")
        Role role
) {
}
