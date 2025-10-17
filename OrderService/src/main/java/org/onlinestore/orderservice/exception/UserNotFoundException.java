package org.onlinestore.orderservice.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Пользователь не существует");
    }
}
