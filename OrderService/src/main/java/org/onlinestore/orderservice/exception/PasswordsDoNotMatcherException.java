package org.onlinestore.orderservice.exception;

/**
 * Исключение, выбрасываемое при несоответствии паролей
 * (например, при регистрации, когда пароль и его подтверждение не совпадают).
 * Обычно обрабатывается глобальным обработчиком исключений {@link GlobalExceptionHandler}.
 */
public class PasswordsDoNotMatcherException extends RuntimeException {

    /**
     * Создает новое исключение с предопределенным сообщением.
     */
    public PasswordsDoNotMatcherException() {
        super("Пароли не совпадают");
    }
}