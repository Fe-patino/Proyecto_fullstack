package notificaciones.notificaciones;

import notificaciones.notificaciones.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ErrorDTO(
                LocalDateTime.now(), 400, "Error de validación", errores, request.getRequestURI()
        ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarBD(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("base de datos", "Ya existe un registro con esos datos");

        return ResponseEntity.status(409).body(new ErrorDTO(
                LocalDateTime.now(), 409, "Conflicto en la base de datos", errores, request.getRequestURI()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGenerico(
            Exception ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage());

        return ResponseEntity.internalServerError().body(new ErrorDTO(
                LocalDateTime.now(), 500, "Error interno del servidor", errores, request.getRequestURI()
        ));
    }
}
