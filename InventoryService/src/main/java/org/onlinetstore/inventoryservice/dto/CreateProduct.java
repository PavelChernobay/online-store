package org.onlinetstore.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * DTO для создания нового продукта.
 * Используется при запросе на добавление продукта через {@link org.onlinetstore.inventoryservice.controller.ProductController}.
 *
 * @param name     название продукта, не может быть пустым
 * @param quantity количество продукта на складе, обязательно для заполнения
 * @param price    цена продукта, обязательно для заполнения
 * @param sale     скидка на продукт (в процентах), необязательное поле
 */
@Builder
public record CreateProduct(

        @NotBlank(message = "Введите название продукта")
        String name,

        @NotNull(message = "Введите количество товара")
        Integer quantity,

        @NotNull(message = "Введите цену товара")
        BigDecimal price,

        Integer sale
) {
}
