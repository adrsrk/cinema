package com.app.model.booking;

import java.util.List;

public record TakenSeatsResponseDTO(
        Long sessionId,
        List<SeatDTO> takenSeats
) {
}
