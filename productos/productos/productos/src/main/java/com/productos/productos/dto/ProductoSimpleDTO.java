package com.productos.productos.dto;

import lombok.Data;

// DTO simple para union de servicios
// Se usa para devolver solo los datos minimos necesarios
// No es una entidad, no se guarda en la base de datos
@Data // crea @Getter, @Setter etc
public class ProductoSimpleDTO {

    private Long id;
    private String nombre;

}
