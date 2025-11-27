package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record AnalyticsKafkaEvent(
        Long id,
        UUID orderId,
        UUID productId,
        UUID userId,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal totalPrice,
        String traceId
) {
}
