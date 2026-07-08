package com.carrito.carrito.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorDTO(
    LocalDateTime timestamp,
    int status,
    String error,
    Map<String, String> errors,
    String path
) {}
