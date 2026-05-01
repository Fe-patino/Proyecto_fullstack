package com.carrito.carrito.dto;

import java.util.List;

public record CarritoResumenDTO(
    Integer usuarioId,
    List<CarritoItemResponseDTO> items,
    int totalItems,
    Double totalCarrito
) {}
