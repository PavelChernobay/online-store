package org.onlinestore.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateProduct {

        @NotBlank(message = "Введите название продукта")
        private String productName;

        @NotNull(message = "Введите количество продукта")
        private int quantity;

        private UUID traceId;

}
