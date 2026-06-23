package producto.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoListadoDTO {

    private Integer id;
    private String nombre;
    private Double precio;
    private Boolean disponible;
}
