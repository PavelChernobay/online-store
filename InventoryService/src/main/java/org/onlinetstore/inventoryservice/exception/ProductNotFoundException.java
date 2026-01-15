package org.onlinetstore.inventoryservice.exception;

/**
 * Исключение, выбрасываемое при попытке получить или удалить продукт,
 * который не существует в системе InventoryService.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Создает новое исключение с сообщением по умолчанию.
     */
    public ProductNotFoundException() {
        super("Данного продукта нет");
    }
}
