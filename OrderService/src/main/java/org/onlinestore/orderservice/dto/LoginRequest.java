package org.onlinestore.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO для запроса аутентификации пользователя.
 * Используется при логине через {@link org.onlinestore.orderservice.controller.AuthController}.
 *
 * @param username имя пользователя, обязательно для заполнения
 * @param password пароль пользователя, обязательно для заполнения
 */
@Builder
public record LoginRequest(

        @NotBlank(message = "Введите имя пользователя")
        String username,

        @NotBlank(message = "Введите пароль пользователя")
        String password
) {
}
