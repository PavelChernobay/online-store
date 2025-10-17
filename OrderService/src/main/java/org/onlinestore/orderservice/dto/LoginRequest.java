package org.onlinestore.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(

        @NotBlank(message = "Введите имя пользователя")
        String username,

        @NotBlank(message = "Введите пароль пользователя")
        String password
) {
}
