package com.app.controller;

import com.app.model.MessageResponseDTO;
import com.app.model.auth.AuthResponseDTO;
import com.app.model.auth.LoginRequestDTO;
import com.app.model.auth.RefreshTokenRequest;
import com.app.model.auth.RegisterRequestDTO;
import com.app.service.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        authServiceImpl.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDTO("Пользователь успешно зарегистрирован"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO response = authServiceImpl.login(loginRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequest request) {

        AuthResponseDTO response = authServiceImpl.refreshToken(request.refreshToken());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDTO> logout(@RequestBody RefreshTokenRequest request) {
        authServiceImpl.logout(request.refreshToken());
        return ResponseEntity.ok(new MessageResponseDTO("Вы успешно вышли из системы"));
    }
}
