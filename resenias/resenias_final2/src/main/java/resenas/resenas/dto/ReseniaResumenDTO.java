package resenas.resenas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniaResumenDTO {

    private Integer restauranteId;
    private long totalResenias;
    private Double promedioRestaurante;
    private Double promedioRepartidor;
}
