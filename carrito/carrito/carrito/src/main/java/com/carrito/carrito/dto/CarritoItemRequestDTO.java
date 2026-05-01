package com.carrito.carrito.dto;

import jakarta.validation.constraints.*;

public record CarritoItemRequestDTO(

    @NotNull(message = "El ID de usuario es obligatorio")
    Integer usuarioId,

    @NotNull(message = "El ID de restaurante es obligatorio")
    Integer restauranteId,

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    String nombreProducto,

    @NotBlank(message = "El SKU del producto es obligatorio")
    String skuProducto,

    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres")
    String descripcion,

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    Integer cantidad,

    @NotNull(message = "El precio unitario es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    Double precioUnitario
) {}
