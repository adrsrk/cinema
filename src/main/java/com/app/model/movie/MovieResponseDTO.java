package com.app.model.movie;

import java.time.Instant;

public record MovieResponseDTO(
        Long id,
        String title,
        String description,
        int duration,
        String posterUrl,
        Instant createdAt
) {
}
