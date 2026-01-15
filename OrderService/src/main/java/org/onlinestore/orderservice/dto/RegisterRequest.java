package org.onlinestore.orderservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * DTO для регистрации нового пользователя.
 * Используется при запросе на регистрацию через {@link org.onlinestore.orderservice.controller.AuthController}.
 *
 * @param username       имя пользователя, обязательное для заполнения, длина от 4 до 10 символов
 * @param email          email пользователя, должен быть валидным
 * @param password       пароль пользователя, обязательное поле, длина от 10 до 20 символов
 * @param repeatPassword повтор пароля для подтверждения, обязательное поле
 */
@Builder
public record RegisterRequest(

        @NotBlank(message = "Введите имя для регистрации")
        @Size(min = 4, max = 10, message = "Имя пользователя должно содержать от 4 до 10 символов")
        String username,

        @Email(message = "Не валидный email")
        String email,

        @NotBlank(message = "Введите пароль для регистрации")
        @Size(min = 4, max = 20, message = "Пароль пользователя должно содержать от 10 до 20 символов")
        String password,

        @NotBlank(message = "Повторите пароль для регистрации")
        String repeatPassword
) {
}
