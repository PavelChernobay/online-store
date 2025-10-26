package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record BasketResponse(
        UUID id,
        UUID userId,
        List<BasketProductResponse> products,
        BigDecimal totalSum
) {
}
