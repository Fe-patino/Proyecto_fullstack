package com.repartidores.repartidores.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;  // Fecha y hora del error
    private int status;               // Código HTTP (400, 404, 500)
    private String mensaje;           // Mensaje general del error
    private Map<String, String> errores; // Detalle por campo (ej: "nombre" -> "no puede estar vacío")
    private String path;              // URL donde ocurrió el error

    // Constructor completo
    public ErrorDTO(LocalDateTime timestamp, int status, String mensaje,
                    Map<String, String> errores, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;

    }


}
