package com.app.model.booking;

import java.time.Instant;
import java.util.List;

public record BookingResponseDTO(
        Long id,
        Long sessionId,
        List<SeatDTO> seats,
        Instant createdAt
) {
}
