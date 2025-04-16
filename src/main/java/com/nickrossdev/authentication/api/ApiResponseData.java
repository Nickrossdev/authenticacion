package com.nickrossdev.authentication.api;

import org.aspectj.bridge.Message;

public record ApiResponseData<T>(
        Integer code,
        Boolean success,
        Message message,
        T data
) {
}