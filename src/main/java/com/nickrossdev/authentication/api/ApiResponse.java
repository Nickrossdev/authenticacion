package com.nickrossdev.authentication.api;

public record ApiResponse(
        Integer code,
        Boolean success,
        String message
) {
}
