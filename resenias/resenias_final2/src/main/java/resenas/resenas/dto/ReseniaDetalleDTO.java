package resenas.resenas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniaDetalleDTO {

    private Integer id;
    private Integer pedidoId;
    private Integer usuarioId;
    private Integer restauranteId;
    private Integer puntuacionRestaurante;
    private Integer puntuacionRepartidor;
    private String comentario;
    private String fechaCreacion;
}
