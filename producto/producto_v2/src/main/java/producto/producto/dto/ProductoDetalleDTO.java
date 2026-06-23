package producto.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDetalleDTO {

    private Integer id;
    private Integer idRestauranteRef;
    private Integer idCategoriaRef;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean disponible;
}
