package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record BasketProductResponse(
        UUID id,
        UUID productId,
        UUID basketId,
        String name,
        int quantity,
        BigDecimal price,
        int sale,
        BigDecimal totalSum
) {
}
