package com.usuario.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioListadoDTO {

    private Integer id;
    private String nombreCompleto;
    private String email;
    private String telefono;

}
