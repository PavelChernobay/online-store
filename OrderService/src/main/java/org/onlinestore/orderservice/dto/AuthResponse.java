package org.onlinestore.orderservice.dto;

import lombok.Builder;

/**
 * DTO для ответа аутентификации.
 * <p>
 * Содержит JWT-токен для доступа и токен обновления (refresh token).
 */
@Builder
public record AuthResponse(

        /**
         * JWT-токен для доступа к защищённым ресурсам.
         */
        String token,

        /**
         * Токен обновления для получения нового JWT-токена после истечения срока действия.
         */
        String refreshToken
) {
}
