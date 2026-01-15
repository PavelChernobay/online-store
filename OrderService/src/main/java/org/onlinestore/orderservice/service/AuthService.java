package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.AuthResponse;
import org.onlinestore.orderservice.dto.LoginRequest;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;

/**
 * Сервис для работы с аутентификацией и регистрацией пользователей.
 * Содержит методы для регистрации, входа в систему и обновления токена.
 */
public interface AuthService {

    /**
     * Регистрирует нового пользователя.
     *
     * @param registerRequest DTO с данными для регистрации {@link RegisterRequest}
     * @return DTO пользователя {@link UserResponse}
     */
    UserResponse register(RegisterRequest registerRequest);

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param loginRequest DTO с данными для входа {@link LoginRequest}
     * @return DTO с токенами аутентификации {@link AuthResponse}
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Обновляет токен доступа с использованием refresh-токена.
     *
     * @param refreshToken токен обновления
     * @return DTO с новыми токенами аутентификации {@link AuthResponse}
     */
    AuthResponse refresh(String refreshToken);

}