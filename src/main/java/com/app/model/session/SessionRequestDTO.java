package com.app.model.session;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record SessionRequestDTO(
        @NotNull(message = "Необходимо указать фильм")
        Long movieId,
        @NotNull(message = "Необходимо указать зал")
        Long hallId,
        @NotNull(message = "Необходимо указать время начала сеанса")
        Instant startTime,
        @NotNull(message = "Необходимо указать цену")
        @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
        BigDecimal price
) {
}
