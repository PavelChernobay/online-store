package org.onlinestore.notificationservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для представления информации о заказе.
 * Используется для передачи данных о заказах между слоями приложения.
 *
 * @param id          идентификатор записи заказа в системе уведомлений
 * @param orderId     идентификатор заказа
 * @param productId   идентификатор товара
 * @param userId      идентификатор пользователя, совершившего заказ
 * @param quantity    количество товара
 * @param price       цена единицы товара
 * @param sale        процент скидки
 * @param totalPrice  итоговая стоимость заказа с учетом скидки
 */
@Builder
public record OrderResponse(
        UUID id,
        UUID orderId,
        UUID productId,
        UUID userId,
        Integer quantity,
        BigDecimal price,
        Integer sale,
        BigDecimal totalPrice
) {
}
