package com.app.model.booking;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookingRequestDTO(
        @NotNull(message = "Необходимо указать сеанс")
        Long sessionId,
        @NotNull(message = "Необходимо указать места")
        List<SeatDTO> seats
) {
}
