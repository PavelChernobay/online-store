package org.onlinestore.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.AuthResponse;
import org.onlinestore.orderservice.dto.LoginRequest;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер для аутентификации и регистрации пользователей.
 * <p>
 * Предоставляет эндпоинты для регистрации, входа в систему и обновления JWT-токенов.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрирует нового пользователя.
     *
     * @param registerRequest DTO {@link RegisterRequest} с данными нового пользователя
     * @return DTO {@link UserResponse} с информацией о созданном пользователе
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param loginRequest DTO {@link LoginRequest} с логином и паролем
     * @return DTO {@link AuthResponse} с JWT-токенами (access и refresh)
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    /**
     * Обновляет access-токен с помощью refresh-токена.
     *
     * @param refreshToken refresh-токен пользователя
     * @return DTO {@link AuthResponse} с новым JWT-токеном
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

}
