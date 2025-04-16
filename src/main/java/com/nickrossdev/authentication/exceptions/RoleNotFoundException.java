package com.nickrossdev.authentication.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("No existe el rol: " + name);
    }
}
