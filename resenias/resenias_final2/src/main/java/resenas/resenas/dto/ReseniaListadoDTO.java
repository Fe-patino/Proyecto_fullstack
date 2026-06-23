package resenas.resenas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniaListadoDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer pedidoId;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El ID de restaurante es obligatorio")
    private Integer restauranteId;

    @NotNull(message = "La puntuación del restaurante es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer puntuacionRestaurante;

    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer puntuacionRepartidor;

    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    private String comentario;
}
