package org.onlinestore.orderservice.exception;

public class ProductNotFoundException extends RuntimeException {

    public static final String PRODUCT_NOT_FOUND = "Товара не существует";
    public static final String PRODUCT_IS_OUT_OF_STOCK = "Продукт %s закончился";
    public static final String FEW_PRODUCT_IN_STOCK = "Недостаточно товара на складе. Остаток товара %d шт.";
    public static final String PRODUCT_NOT_FOUND_TO_BASKET = "Товара в корзине нет";

    public ProductNotFoundException(String message) {
        super(message);
    }

}
