package org.onlinestore.orderservice.exception;

/**
 * Исключение, выбрасываемое при попытке регистрации пользователя,
 * когда пользователь с таким именем уже существует в базе данных.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /** Сообщение об ошибке для ситуации, когда пользователь уже существует */
    public static final String USER_ALREADY_EXISTS = "Пользователь с таким именем уже существует";

    /**
     * Создает новое исключение с предопределенным сообщением.
     */
    public UserAlreadyExistsException() {
        super(USER_ALREADY_EXISTS);
    }
}