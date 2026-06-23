package resenas.resenas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Referencia al pedido en ms-pedidos (sin FK real entre microservicios)
    @NotNull(message = "El ID del pedido es obligatorio")
    @Column(name = "pedido_id", nullable = false, unique = true)
    private Integer pedidoId;

    // Referencia al usuario en ms-usuario (sin FK real entre microservicios)
    @NotNull(message = "El ID de usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    // Referencia al restaurante en ms-restaurante (sin FK real entre microservicios)
    @NotNull(message = "El ID de restaurante es obligatorio")
    @Column(name = "restaurante_id", nullable = false)
    private Integer restauranteId;

    @NotNull(message = "La puntuación del restaurante es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    @Column(name = "puntuacion_restaurante", nullable = false)
    private Integer puntuacionRestaurante;

    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    @Column(name = "puntuacion_repartidor")
    private Integer puntuacionRepartidor;

    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    @Column(length = 500)
    private String comentario;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}