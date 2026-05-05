package com.resenias.resenias.dto;

public record ReseniaResponseDTO(
    Integer id,
    Integer pedidoId,
    Integer usuarioId,
    Integer restauranteId,
    Integer puntuacionRestaurante,
    Integer puntuacionRepartidor,
    String comentario,
    String fechaCreacion
) {}
