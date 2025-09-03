package com.app.model;

import java.time.LocalDateTime;

public record MovieResponseDTO(
        Long id,
        String title,
        String description,
        int duration,
        String posterUrl,
        LocalDateTime createdAt
) {
}
