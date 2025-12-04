package org.onlinestore.orderservice.exception;

/**
 * Исключение, выбрасываемое при попытке использовать недействительный
 * refresh-токен. Обычно обрабатывается глобальным обработчиком исключений
 * {@link GlobalExceptionHandler}.
 */
public class InvalidRefreshTokenException extends RuntimeException {

    /**
     * Создает новое исключение с предопределенным сообщением.
     */
    public InvalidRefreshTokenException() {
        super("Не валидный токен, необходимо авторизоваться!");
    }
}