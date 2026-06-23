package com.pedidos.pedidos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Vinculación con otros microservicios
    @NotNull(message = "El ID de usuario es obligatorio")
    @Column(nullable = false)
    private Integer usuarioId;

    @NotNull(message = "El ID de restaurante es obligatorio")
    @Column(nullable = false)
    private Integer restauranteId;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El SKU es obligatorio")
    @Column(nullable = false, length = 14)
    private String sku;

    @Column(length = 250)
    private String descripcion;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private Double cantidad;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private String estado = "PENDIENTE"; // PENDIENTE, PAGADO, EN_PREPARACION, EN_CAMINO, ENTREGADO

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}