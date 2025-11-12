package org.onlinetstore.inventoryservice.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Данного продукта нет");
    }
}
