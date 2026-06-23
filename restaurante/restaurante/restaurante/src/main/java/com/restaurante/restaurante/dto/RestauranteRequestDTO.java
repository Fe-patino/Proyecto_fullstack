package com.restaurante.restaurante.dto;

import jakarta.validation.constraints.*;

public record RestauranteRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotBlank(message = "La dirección es obligatoria")
    String direccion,
    
    String tipoComida,
    
    String horario,

    @Min(0) @Max(5)
    Double calificacion
) {}