package org.onlinestore.orderservice.exception;

public class BasketNotFoundException extends RuntimeException {

    public BasketNotFoundException() {
        super("У Вас еще нет корзины, нужно добавить товар");
    }
}
