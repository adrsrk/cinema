package com.app.service;

import com.app.model.auth.AuthResponseDTO;
import com.app.model.auth.LoginRequestDTO;
import com.app.model.auth.RegisterRequestDTO;

public interface AuthService {

    void register(RegisterRequestDTO registerRequestDTO);

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);

    AuthResponseDTO refreshToken(String refreshToken);

    void logout(String refreshToken);
}
