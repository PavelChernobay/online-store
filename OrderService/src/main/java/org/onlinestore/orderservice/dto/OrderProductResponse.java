package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderProductResponse(
        UUID id,
        UUID productId,
        UUID orderId,
        String name,
        int quantity,
        BigDecimal price,
        int sale,
        BigDecimal totalSum
) {
}
