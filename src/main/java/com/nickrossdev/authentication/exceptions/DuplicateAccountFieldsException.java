package com.nickrossdev.authentication.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class DuplicateAccountFieldsException extends RuntimeException {
    private final Map<String, String> errors;
    public DuplicateAccountFieldsException(Map<String, String> errors) {
        super("Error de campos únicos");
        this.errors = errors;
    }
}
