package com.nickrossdev.authentication.api;

public record ApiErrorData<T>(
        int status,
        String error,
        Boolean success,
        String message,
        T details
) {
}
