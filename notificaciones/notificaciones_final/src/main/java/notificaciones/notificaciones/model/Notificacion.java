package notificaciones.notificaciones.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Referencia al usuario destinatario (ms-usuario)
    @NotNull(message = "El ID de usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    // Tipo de destinatario: CLIENTE, RESTAURANTE, REPARTIDOR, ADMIN
    @NotBlank(message = "El tipo de destinatario es obligatorio")
    @Column(name = "tipo_destinatario", nullable = false, length = 20)
    private String tipoDestinatario;

    // Tipos: PEDIDO_CONFIRMADO, REPARTIDOR_ASIGNADO, PEDIDO_EN_CAMINO,
    //        PEDIDO_ENTREGADO, PAGO_APROBADO, PAGO_RECHAZADO, PEDIDO_CANCELADO,
    //        RESENIA_RECIBIDA, NUEVO_PEDIDO, RESENIA_NUEVA, PEDIDO_ASIGNADO,
    //        NUEVA_ZONA_DISPONIBLE, PAGO_FALLIDO_REPETIDO, SIN_REPARTIDORES_DISPONIBLES
    @NotBlank(message = "El tipo de notificacion es obligatorio")
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    // Canal: PUSH, EMAIL, SMS
    @NotBlank(message = "El canal es obligatorio")
    @Column(nullable = false, length = 10)
    private String canal;

    @NotBlank(message = "El titulo es obligatorio")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(name = "pedido_id")
    private Integer pedidoId;

    @Column(name = "restaurante_id")
    private Integer restauranteId;

    @Column(nullable = false)
    private Boolean leida = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}
