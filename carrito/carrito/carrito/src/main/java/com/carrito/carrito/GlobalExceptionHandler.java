package com.carrito.carrito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura los errores personalizados lanzados desde el servicio (ej: usuario o restaurante no encontrado)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.setError("Error de validación de negocio");
        errorResponse.put("mensaje", ex.getMessage());
        
        // Retornamos un 400 Bad Request en lugar de un 500 feo
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}