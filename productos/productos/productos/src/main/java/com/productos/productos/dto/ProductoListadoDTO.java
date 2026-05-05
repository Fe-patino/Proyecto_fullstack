package com.productos.productos.dto;

import lombok.Data;

// DTO para devolver datos del producto al listar
// No es una entidad, no se guarda en la base de datos
@Data // crea @Getter, @Setter etc
public class ProductoListadoDTO {

    private Long id;
    private String nombre;
    private Double precio;
    private Boolean disponible;

}
