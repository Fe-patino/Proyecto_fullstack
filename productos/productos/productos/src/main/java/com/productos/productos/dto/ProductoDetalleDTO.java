package com.productos.productos.dto;

import lombok.Data;

// DTO para devolver el detalle completo de un producto
// No es una entidad, no se guarda en la base de datos
@Data // crea @Getter, @Setter etc
public class ProductoDetalleDTO {

    private Long id;
    private Long idRestauranteRef;
    private Long idCategoriaRef;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean disponible;

}
