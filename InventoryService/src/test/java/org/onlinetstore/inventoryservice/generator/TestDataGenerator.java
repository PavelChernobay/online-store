package org.onlinetstore.inventoryservice.generator;

import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.onlinetstore.inventoryservice.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class TestDataGenerator {

    public static final String PRODUCT_NAME = "Яблоко";
    public static final int QUANTITY = 5;
    public static final BigDecimal PRICE = BigDecimal.valueOf(10);
    public static final String PRODUCT_N0T_FOUND = "Данного продукта нет";

    public static Product generateProduct() {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .build();
    }

    public static ProductResponse generateProductResponse() {
        return ProductResponse.builder()
                .id(UUID.randomUUID())
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .build();
    }

    public static CreateProduct generateCreateProduct() {
        return CreateProduct.builder()
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .build();
    }

}
