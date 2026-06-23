package com.pedidos.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HojaDespachoDTO {

    private Integer pedidoId;
    private String nombreCliente;
    private String telefonoCliente;
    private String direccionEntrega;
    private String nombreRestaurante;
    private String direccionRestaurante;
    private String producto;
    private Double cantidad;
    private String estadoPedido;
    private String notasConductor;
}