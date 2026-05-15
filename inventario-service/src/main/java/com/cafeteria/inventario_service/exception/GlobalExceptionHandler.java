package com.cafeteria.inventario_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 Recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(
            ResourceNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("error", ex.getMessage());
        error.put("status", 404);
        error.put("timestamp", LocalDateTime.now());

        return error;
    }

    // 🟠 Reglas de negocio
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBusiness(
            BusinessException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("error", ex.getMessage());
        error.put("status", 400);
        error.put("timestamp", LocalDateTime.now());

        return error;
    }

    // 🟡 Validaciones DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        return errors;
    }

    // 🔥 Error general
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGeneral(Exception ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("error", ex.getMessage());
        error.put("status", 500);
        error.put("timestamp", LocalDateTime.now());

        return error;
    }
}