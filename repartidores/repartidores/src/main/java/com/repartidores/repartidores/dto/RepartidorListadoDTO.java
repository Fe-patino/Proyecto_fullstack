package com.repartidores.repartidores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RepartidorListadoDTO {

    private Integer id;
    private String nombreCompleto;
    private String telefono;
    private String tipoVehiculo;
    private Boolean disponible;

}
