package org.onlinestore.orderservice.exception;

/**
 * Исключение, выбрасываемое при попытке получить пользователя,
 * который отсутствует в базе данных.
 */
public class UserNotFoundException extends RuntimeException {

    /** Сообщение об ошибке для ситуации, когда пользователь не найден */
    public static final String USER_NOT_FOUND = "Пользователь не существует";

    /**
     * Создает новое исключение с предопределенным сообщением.
     */
    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}