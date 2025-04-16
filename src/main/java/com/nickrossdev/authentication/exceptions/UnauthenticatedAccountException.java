package com.nickrossdev.authentication.exceptions;

public class UnauthenticatedAccountException extends RuntimeException {
    public UnauthenticatedAccountException() {
        super("No hay cuenta autenticada");
    }
}
