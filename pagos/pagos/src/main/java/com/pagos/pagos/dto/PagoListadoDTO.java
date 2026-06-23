package com.pagos.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoListadoDTO {

    private Integer id;
    private Integer idPedido;
    private Integer idUsuario;
    private String metodoPago;
    private String estado;

}
