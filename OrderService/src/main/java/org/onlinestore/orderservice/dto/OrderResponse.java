package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO для представления информации о заказе.
 * Используется при возврате данных заказа через {@link org.onlinestore.orderservice.controller.OrderController}.
 *
 * @param id        уникальный идентификатор заказа
 * @param userId    уникальный идентификатор пользователя, оформившего заказ
 * @param products  список продуктов в заказе {@link OrderProductResponse}
 * @param status    текущий статус заказа (например, "CREATED", "PAID", "SHIPPED")
 * @param createdAt дата и время создания заказа
 * @param totalSum  итоговая сумма заказа с учетом количества и скидок на продукты
 */
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
