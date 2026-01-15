package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTO для информации о корзине пользователя.
 * Используется при запросах к {@link org.onlinestore.orderservice.controller.BasketController}.
 *
 * @param id        уникальный идентификатор корзины
 * @param userId    идентификатор пользователя, которому принадлежит корзина
 * @param products  список продуктов в корзине, каждый элемент представлен {@link BasketProductResponse}
 * @param totalSum  общая стоимость всех продуктов в корзине с учётом скидок
 */
@Builder
public record BasketResponse(
        UUID id,
        UUID userId,
        List<BasketProductResponse> products,
        BigDecimal totalSum
) {
}
