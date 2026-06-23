package com.carrito.carrito.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Column(nullable = false)
    private Integer usuarioId;

    @NotNull(message = "El ID de restaurante es obligatorio")
    @Column(nullable = false)
    private Integer restauranteId;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @NotBlank(message = "El SKU del producto es obligatorio")
    @Column(nullable = false, length = 14)
    private String skuProducto;

    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres")
    @Column(length = 250)
    private String descripcion;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private String estado = "ACTIVO";

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaAgregado = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @Transient
    public Double getSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            return cantidad * precioUnitario;
        }
        return 0.0;
    }
}
