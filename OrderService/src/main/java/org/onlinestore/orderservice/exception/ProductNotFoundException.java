package org.onlinestore.orderservice.exception;

/**
 * Исключение, выбрасываемое при отсутствии продукта в базе данных
 * или при попытке взаимодействия с продуктом, которого нет на складе/в корзине.
 * Используются предопределенные сообщения для разных сценариев.
 */
public class ProductNotFoundException extends RuntimeException {

    /** Сообщение: продукт не найден */
    public static final String PRODUCT_NOT_FOUND = "Товара не существует";

    /** Сообщение: продукт закончился */
    public static final String PRODUCT_IS_OUT_OF_STOCK = "Продукт %s закончился";

    /** Сообщение: недостаточно товара на складе */
    public static final String FEW_PRODUCT_IN_STOCK = "Недостаточно товара на складе. Остаток товара %d шт.";

    /** Сообщение: продукт отсутствует в корзине */
    public static final String PRODUCT_NOT_FOUND_TO_BASKET = "Товара в корзине нет";

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение исключения
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}