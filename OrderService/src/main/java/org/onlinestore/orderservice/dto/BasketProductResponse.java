package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для информации о продукте в корзине.
 * Используется при запросах к {@link org.onlinestore.orderservice.controller.BasketController}.
 *
 * @param id        уникальный идентификатор записи корзины
 * @param productId идентификатор продукта
 * @param basketId  идентификатор корзины пользователя
 * @param name      название продукта
 * @param quantity  количество данного продукта в корзине
 * @param price     цена за единицу продукта
 * @param sale      скидка на продукт (в процентах)
 * @param totalSum  общая стоимость продукта с учётом количества и скидки
 */
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
