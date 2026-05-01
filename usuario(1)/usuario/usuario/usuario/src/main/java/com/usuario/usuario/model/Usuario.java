package com.usuario.usuario.model;

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
@Data
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El RUN es obligatorio")
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

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    @NotBlank(message = "La región es obligatoria")
    private String region;

    @NotBlank(message = "El país es obligatorio")
    private String pais;




}
