package com.nickrossdev.authentication.api;

import java.time.LocalDateTime;

public record ApiError(
        Integer code,
        String error,
        Boolean success,
        String message
) {
}
