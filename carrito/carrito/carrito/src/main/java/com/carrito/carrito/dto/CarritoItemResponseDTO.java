package com.carrito.carrito.dto;

import java.time.LocalDateTime;

public record CarritoItemResponseDTO(
    Integer id,
    Integer usuarioId,
    Integer restauranteId,
    String nombreProducto,
    String skuProducto,
    String descripcion,
    Integer cantidad,
    Double precioUnitario,
    Double subtotal,
    String estado,
    LocalDateTime fechaAgregado,
    LocalDateTime fechaActualizacion
) {}
