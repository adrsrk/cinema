package com.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record MovieRequestDTO(
        @NotBlank (message = "Please indicate the title")
        @Size (max = 255, message = "title must be less than 255 characters")
        String title,
        @NotBlank (message = "Please indicate the description")
        @Size(max = 1000, message = "description must be less than 1000 characters")
        String description,
        @Positive (message = "Please indicate the duration (must be > 0)")
        int duration,
        @NotBlank (message = "Please indicate the posterUrl")
        @URL(message = "posterUrl must be a valid URL")
        String posterUrl
) {
}
