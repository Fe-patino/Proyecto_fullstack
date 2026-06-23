package com.pedidos.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteResponseDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String tipoComida;
    private String horario;
    private Double calificacion;
    private Boolean abierto;
}