package com.nickrossdev.authentication.config;

import com.nickrossdev.authentication.api.ApiError;
import com.nickrossdev.authentication.api.ApiErrorData;
import com.nickrossdev.authentication.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  Cuando se accede a una ruta que no existe
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandlerFoundException() {
        return buildApiError(HttpStatus.NOT_FOUND, "Ruta no encontrada");
    }

    //  Cuando la autenticación falla (usuario no logueado o token inválido)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        return buildApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    //  Cuando la validación de un @RequestBody falla (campos inválidos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorData<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        return buildApiErrorData(HttpStatus.BAD_REQUEST, "Error al validar", fieldErrors);
    }

    //  Cuando se intenta crear una cuenta con campos ya existentes (como email, username)
    @ExceptionHandler(DuplicateAccountFieldsException.class)
    public ResponseEntity<ApiErrorData<Map<String, String>>> handleDuplicateAccountFieldsException(DuplicateAccountFieldsException ex) {
        return buildApiErrorData(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getErrors());
    }

    //  Cuando la contraseña ingresada es incorrecta (pero el usuario existe)
    @ExceptionHandler(AccountPasswordIncorrectException.class)
    public ResponseEntity<ApiError> handleAccountPasswordIncorrectException(AccountPasswordIncorrectException ex) {
        return buildApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Cuando no se encuentra una cuenta por ID o email
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNotFoundException(AccountNotFoundException ex) {
        return buildApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Cuando no se encuentra un rol requerido en la base de datos
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiError> handleRoleNotFoundException(RoleNotFoundException ex) {
        return buildApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Cuando algún argumento pasado al backend no es válido o está mal formado
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //  Cuando un usuario autenticado intenta acceder a un recurso que no tiene permiso
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        return buildApiError(HttpStatus.FORBIDDEN, "No tienes permiso para acceder a este recurso.");
    }

    //  Cuando hace logout sin tener ya la cookie disponible
    @ExceptionHandler(UnauthenticatedAccountException.class)
    public ResponseEntity<ApiError> handleUnauthenticatedAccountException(UnauthenticatedAccountException ex) {
        return buildApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }


    //  Cualquier excepción no manejada explícitamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception e) {
        return buildApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    //  Constructor de respuesta para errores simples (sin datos extra)
    private ResponseEntity<ApiError> buildApiError(HttpStatus status, String message) {
        ApiError apiError = new ApiError(status.value(), status.getReasonPhrase(), false, message);
        return ResponseEntity.status(status).body(apiError);
    }

    //  Constructor de respuesta para errores con datos adicionales (por ejemplo, errores de validación)
    private <T> ResponseEntity<ApiErrorData<T>> buildApiErrorData(HttpStatus status, String message, T data) {
        ApiErrorData<T> apiErrorData = new ApiErrorData<>(status.value(), status.getReasonPhrase(), false, message, data);
        return ResponseEntity.status(status).body(apiErrorData);
    }
}
