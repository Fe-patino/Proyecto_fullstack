package com.productos.productos.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "el id de restaurante no puede estar vacio")
    @Column(name = "id_restaurante_ref", nullable = false)
    private Long idRestauranteRef;

    @NotNull(message = "el id de categoria no puede estar nulo")
    @Column(name = "id_categoria_ref", nullable = false)
    private Long idCategoriaRef;

    @NotNull(message = "el nombre no puede estar vacio")
    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 300)
    private String descripcion;

    @NotNull(message = "el precio no puede estar vacio")
    @Min(value = 0, message = "el precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Boolean disponible;

}
