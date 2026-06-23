package notificaciones.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionListadoDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer usuarioId;

    @NotBlank(message = "El tipo de destinatario es obligatorio")
    private String tipoDestinatario;

    @NotBlank(message = "El tipo de notificacion es obligatorio")
    private String tipo;

    @NotBlank(message = "El canal es obligatorio")
    private String canal;

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    private Integer pedidoId;

    private Integer restauranteId;
}
