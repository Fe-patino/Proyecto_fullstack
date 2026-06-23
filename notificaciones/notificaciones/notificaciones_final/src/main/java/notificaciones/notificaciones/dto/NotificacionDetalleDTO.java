package notificaciones.notificaciones.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDetalleDTO {

    private Integer id;
    private Integer usuarioId;
    private String tipoDestinatario;
    private String tipo;
    private String canal;
    private String titulo;
    private String mensaje;
    private Integer pedidoId;
    private Integer restauranteId;
    private Boolean leida;
    private String fechaEnvio;
}
