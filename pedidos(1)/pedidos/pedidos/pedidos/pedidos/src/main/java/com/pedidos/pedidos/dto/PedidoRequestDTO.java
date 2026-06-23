package com.pedidos.pedidos.dto;

import jakarta.validation.constraints.*;

public record PedidoRequestDTO(
    @NotNull(message = "El ID de usuario es obligatorio")
    Integer usuarioId,
    
    @NotNull(message = "El ID de restaurante es obligatorio")
    Integer restauranteId,
    
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotBlank(message = "El SKU es obligatorio")
    String sku,
    
    String descripcion,
    
    @NotNull @Positive Double cantidad,
    @NotNull @PositiveOrZero Double precio
) {}