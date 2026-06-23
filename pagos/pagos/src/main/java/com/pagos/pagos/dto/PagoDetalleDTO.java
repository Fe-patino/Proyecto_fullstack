package com.pagos.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDetalleDTO {
    private Integer id;
    private Integer idPedido;
    private Integer idUsuario;
    private String metodoPago;
    private Integer monto;
    private String estado;

}
