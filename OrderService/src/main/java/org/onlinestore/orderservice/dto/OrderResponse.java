package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID orderId,
        UUID userId,
        List<ProductItemResponse> products,
        String status,
        LocalDateTime createAt,
        BigDecimal totalSum
) {
}
