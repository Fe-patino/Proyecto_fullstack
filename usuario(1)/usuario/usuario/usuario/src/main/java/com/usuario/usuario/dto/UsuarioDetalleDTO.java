package com.usuario.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDetalleDTO {

    private Integer id;
    private Integer run;
    private String dv;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String calle;
    private String comuna;
    private String region;
    private String pais;

}
