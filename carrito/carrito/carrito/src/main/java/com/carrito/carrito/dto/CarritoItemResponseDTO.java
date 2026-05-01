package com.carrito.carrito.dto;

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
    String fechaAgregado,
    String fechaActualizacion
) {}
