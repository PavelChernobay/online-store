package org.onlinestore.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateProduct(

        @NotBlank(message = "Введите название продукта")
        String productName,

        @NotNull(message = "Введите количество продукта")
        int quantity
) {
}
