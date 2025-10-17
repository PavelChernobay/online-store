package org.onlinestore.orderservice.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Пользователь с таким именем уже существует");
    }
}
