package org.onlinestore.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * DTO для добавления продукта в корзину пользователя.
 * Используется при запросах к {@link org.onlinestore.orderservice.controller.BasketController}.
 */
@Data
@Builder
public class CreateProduct {

        /** Название продукта, не может быть пустым */
        @NotBlank(message = "Введите название продукта")
        private String productName;

        /** Количество продукта для добавления, обязательно для заполнения */
        @NotNull(message = "Введите количество продукта")
        private int quantity;

        /** Уникальный идентификатор запроса для трассировки (необязательное поле) */
        private UUID traceId;

}
