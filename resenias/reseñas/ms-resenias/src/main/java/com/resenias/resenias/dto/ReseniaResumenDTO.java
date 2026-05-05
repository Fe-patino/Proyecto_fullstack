package com.resenias.resenias.dto;

public record ReseniaResumenDTO(
    Integer restauranteId,
    long totalResenias,
    Double promedioRestaurante,
    Double promedioRepartidor
) {}
