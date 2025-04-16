package com.nickrossdev.authentication.exceptions;

public class AccountPasswordIncorrectException extends RuntimeException {
    public AccountPasswordIncorrectException() {
        super("Contraseña Incorrecta");
    }
}
