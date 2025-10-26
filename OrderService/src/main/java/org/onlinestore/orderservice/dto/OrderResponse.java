package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID id,
        UUID userId,
        List<OrderProductResponse> products,
        String status,
        LocalDateTime createdAt,
        BigDecimal totalSum
) {
}
