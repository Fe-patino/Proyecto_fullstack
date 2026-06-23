package com.repartidores.repartidores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepartidorDetalleDTO {

    
    private Integer id;
    private Integer run;
    private String dv;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String patente;
    private String tipoVehiculo;
    private Boolean disponible;



}
