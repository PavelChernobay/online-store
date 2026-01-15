package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для представления информации о продукте в заказе.
 * Используется при возврате данных заказа через {@link org.onlinestore.orderservice.controller.OrderController}.
 *
 * @param id        уникальный идентификатор записи продукта в заказе
 * @param productId уникальный идентификатор продукта
 * @param orderId   уникальный идентификатор заказа
 * @param name      название продукта
 * @param quantity  количество продукта в заказе
 * @param price     цена за единицу продукта
 * @param sale      скидка на продукт в процентах
 * @param totalSum  итоговая сумма по данному продукту с учетом количества и скидки
 */
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
