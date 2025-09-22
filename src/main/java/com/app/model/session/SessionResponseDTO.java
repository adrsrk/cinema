package com.app.model.session;

import java.math.BigDecimal;
import java.time.Instant;

public record SessionResponseDTO(
        Long id,
        Long movieId,
        Long hallId,
        Instant startTime,
        BigDecimal price
) {
}
