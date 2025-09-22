package com.app.model.hall;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HallRequestDTO(
        @NotBlank
        @Size(max = 255, message = "Название зала должно быть не более 255 символов")
        String name,
        @Positive
        @Max(value = 50, message = "Количество рядов не может быть больше 50")
        int rows,
        @Positive
        @Max(value = 40, message = "Количество мест в ряду не может быть больше 40")
        int seatsPerRow
) {
}
