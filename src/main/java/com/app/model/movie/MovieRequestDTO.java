package com.app.model.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record MovieRequestDTO(
        @NotBlank (message = "Пожалуйста, укажите название")
        @Size (max = 255, message = "Название должно быть не более 255 символов")
        String title,
        @NotBlank (message = "Пожалуйста, укажите описание")
        @Size(max = 1000, message = "Описание должно быть не более 1000 символов")
        String description,
        @Positive (message = "Пожалуйста, укажите длительность (должна быть > 0)")
        int duration,
        @NotBlank (message = "Пожалуйста, укажите URL постера")
        @URL(message = "URL постера должен быть корректным")
        String posterUrl
) {
}
