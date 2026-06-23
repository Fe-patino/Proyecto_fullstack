package com.pedidos.pedidos.dto;

public record PedidoResponseDTO(
    Integer id,
    Integer usuarioId,
    Integer restauranteId,
    String nombre,
    String sku,
    String descripcion,
    Double cantidad,
    Double precio,
    String estado,
    String fechaCreacion 
) {}