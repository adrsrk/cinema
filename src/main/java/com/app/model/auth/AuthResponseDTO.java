package com.app.model.auth;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {
}
