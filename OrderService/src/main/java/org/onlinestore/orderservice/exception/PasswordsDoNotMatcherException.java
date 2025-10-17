package org.onlinestore.orderservice.exception;

public class PasswordsDoNotMatcherException extends RuntimeException {

    public PasswordsDoNotMatcherException() {
        super("Пароли не совпадают");
    }
}
