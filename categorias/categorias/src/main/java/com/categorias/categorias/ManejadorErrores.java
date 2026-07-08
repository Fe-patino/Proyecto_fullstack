package com.categorias.categorias;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.categorias.categorias.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(
        RuntimeException ex,
        HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage()); 

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "Recurso no encontrado",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarErroresValidacion(
                                    MethodArgumentNotValidException ex, 
                                    HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorDTO = new ErrorDTO(
                                LocalDateTime.now(),
                                400,
                                "Error de validacion",
                                errores,
                                request.getRequestURI()
                                );
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErrorBD(
                                    DataIntegrityViolationException ex, 
                                    HttpServletRequest request) {
        
        Map<String, String> errores = new HashMap<>();
        errores.put("nombre", "Ya existe una categoría registrada con ese nombre.");

        ErrorDTO errorDTO = new ErrorDTO(
                                LocalDateTime.now(),
                                409,
                                "Conflicto en la base de datos",
                                errores,
                                request.getRequestURI()
                                );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErroresGlobales(
                                    Exception ex, 
                                    HttpServletRequest request) {
        
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Ocurrió un error inesperado en el servidor.");

        ErrorDTO errorDTO = new ErrorDTO(
                                LocalDateTime.now(),
                                500,
                                "Internal Server Error",
                                errores,
                                request.getRequestURI()
                                );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
