package com.usuario.usuario;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.usuario.usuario.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErrorGeneral(
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

        return ResponseEntity.status(404).body(errorDTO);
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
                "Error de validación",
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
        errores.put("base de datos", "Ya existe un registro con esos datos");

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                409,
                "Conflicto en la base de datos",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(errorDTO);
    }







}
