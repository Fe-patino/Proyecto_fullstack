package com.pagos.pagos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pago {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "El id del pedido es obligatorio")
    @Positive(message = "El id del pedido debe ser un número positivo")
    private Integer idPedido;


    @NotNull(message = "El id del usuario es obligatorio")
    @Positive(message = "El id del usuario debe ser un número positivo")
    private Integer idUsuario;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;          // Esto puede ser "Efectivo", "Tarjeta".  

    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Integer monto;


    @NotBlank(message = "El estado es obligatorio")
    private String estado;            //Esto puede ser "Pendiente", "Completado", "Rechazado"


}
