package org.onlinetstore.inventoryservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для передачи информации о продукте в ответе API.
 * Используется при получении данных о продукте или списке продуктов.
 *
 * @param id       уникальный идентификатор продукта
 * @param name     название продукта
 * @param quantity количество доступного товара
 * @param price    цена продукта
 * @param sale     размер скидки на продукт (если есть)
 */
@Builder
public record ProductResponse(
        UUID id,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer sale
) {
}
