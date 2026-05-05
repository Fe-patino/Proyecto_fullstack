package com.restaurante.restaurante.dto;

public record RestauranteResponseDTO(
    Integer id,
    String nombre,
    String direccion,
    String tipoComida,
    String horario,
    Double calificacion,
    Boolean abierto
) {}