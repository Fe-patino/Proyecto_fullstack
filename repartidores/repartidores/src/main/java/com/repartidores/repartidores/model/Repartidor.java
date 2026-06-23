package com.repartidores.repartidores.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repartidores")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idPedido;

    @NotNull (message = "El RUN es obligatorio")
    @Positive(message = "El RUN debe ser un número positivo")
    private Integer run;

    @NotBlank(message = "El dígito verificador es obligatorio")
    private String dv;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String ap_paterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    private String ap_materno;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La patente es obligatoria")
    private String patente;

    @NotBlank(message = "El tipo de vehiculo es obligatorio")
    private String tipoVehiculo;

    @NotNull(message = "La disponibilidad es obligatoria")
    private Boolean disponible;

}
