package org.onlinestore.orderservice.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String refreshToken
) {
}
