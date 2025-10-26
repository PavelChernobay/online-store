package org.onlinetstore.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

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
