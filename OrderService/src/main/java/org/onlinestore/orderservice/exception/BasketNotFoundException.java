package org.onlinestore.orderservice.exception;

/**
 * Исключение выбрасывается, когда корзина пользователя не найдена.
 * Используется в сервисах работы с корзиной для уведомления, что
 * у пользователя ещё нет созданной корзины.
 */
public class BasketNotFoundException extends RuntimeException {

    /**
     * Создаёт исключение с сообщением по умолчанию.
     */
    public BasketNotFoundException() {
        super("У Вас еще нет корзины, нужно добавить товар");
    }
}