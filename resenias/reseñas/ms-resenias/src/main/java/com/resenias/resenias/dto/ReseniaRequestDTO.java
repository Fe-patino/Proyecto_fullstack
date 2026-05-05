package com.resenias.resenias.dto;

import jakarta.validation.constraints.*;

public record ReseniaRequestDTO(

    @NotNull(message = "El ID del pedido es obligatorio")
    Integer pedidoId,

    @NotNull(message = "El ID de usuario es obligatorio")
    Integer usuarioId,

    @NotNull(message = "El ID de restaurante es obligatorio")
    Integer restauranteId,

    @NotNull(message = "La puntuacion del restaurante es obligatoria")
    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    Integer puntuacionRestaurante,

    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    Integer puntuacionRepartidor,

    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    String comentario

) {}
