package com.app.service;

import com.app.entity.RefreshToken;
import com.app.entity.User;
import com.app.entity.enums.Role;
import com.app.exception.EmailAlreadyExistsException;
import com.app.exception.InvalidRefreshTokenException;
import com.app.model.auth.AuthResponseDTO;
import com.app.model.auth.LoginRequestDTO;
import com.app.model.auth.RegisterRequestDTO;
import com.app.repository.RefreshTokenRepository;
import com.app.repository.UserRepository;
import com.app.util.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void register(RegisterRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.email())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(requestDTO.email());
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        user.setRole(requestDTO.role());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {

        var authToken = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password());

        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String accessToken = jwtService.generateToken(requestDTO.email(), claims, true);

        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put("type", "refresh");
        refreshClaims.put("role", user.getRole().name());

        String refreshToken = jwtService.generateToken(requestDTO.email(), refreshClaims, false);

        RefreshToken rt = new RefreshToken();
        rt.setToken(refreshToken);
        rt.setExpiresAt(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpirationMs()));
        rt.setUser(user);

        refreshTokenRepository.save(rt);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {

        var storedToken  = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidRefreshTokenException("Токен обновления не найден!"));

        if (storedToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new InvalidRefreshTokenException("Срок действия токена обновления истёк!");
        }

        User user = storedToken.getUser();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String accessToken = jwtService.generateToken(user.getEmail(), claims, true);

        refreshTokenRepository.delete(storedToken);

        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put("type", "refresh");
        refreshClaims.put("role", user.getRole().name());

        String newRefreshToken = jwtService.generateToken(user.getEmail(), refreshClaims, false);

        RefreshToken refreshTokenNew = new RefreshToken();
        refreshTokenNew.setToken(newRefreshToken);
        refreshTokenNew.setExpiresAt(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpirationMs()));
        refreshTokenNew.setUser(user);
        refreshTokenRepository.save(refreshTokenNew);

        return new AuthResponseDTO(accessToken, newRefreshToken);
    }

    @Override
    public void logout(String refreshToken) {

        var storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Токен обновления не найден!"));

        refreshTokenRepository.delete(storedToken);
    }
}
