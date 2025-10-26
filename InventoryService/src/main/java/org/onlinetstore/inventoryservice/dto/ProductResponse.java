package org.onlinetstore.inventoryservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductResponse(
        UUID id,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale
) {
}
