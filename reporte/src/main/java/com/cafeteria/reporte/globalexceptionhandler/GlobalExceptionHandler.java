package com.cafeteria.reporte.globalexceptionhandler;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> manejarRuntime(RuntimeException ex){

        ErrorResponse error = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error",
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex){

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Datos invalidos");

        ErrorResponse error = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validacion fallida",
                mensaje
        );

        return ResponseEntity.badRequest().body(error);
    }

}