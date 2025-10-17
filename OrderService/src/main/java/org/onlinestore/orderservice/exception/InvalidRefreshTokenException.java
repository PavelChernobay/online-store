package org.onlinestore.orderservice.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("Не валидный токен, необходимо авторизоваться!");
    }
}
