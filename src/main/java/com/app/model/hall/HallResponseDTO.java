package com.app.model.hall;

public record HallResponseDTO(
        Long id,
        String name,
        int rows,
        int seatsPerRow
) {
}
