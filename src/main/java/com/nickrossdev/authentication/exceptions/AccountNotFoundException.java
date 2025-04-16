package com.nickrossdev.authentication.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String email) {
        super("No existe la cuenta vinculada a: " + email);
    }
}
