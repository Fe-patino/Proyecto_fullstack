package notificaciones.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionResumenDTO {

    private Integer usuarioId;
    private long totalNotificaciones;
    private long noLeidas;
}
