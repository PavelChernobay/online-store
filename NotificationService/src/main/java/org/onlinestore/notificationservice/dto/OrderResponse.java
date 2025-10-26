package org.onlinestore.notificationservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID id,
        UUID orderId,
        UUID productId,
        UUID userId,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal totalPrice
) {
}
