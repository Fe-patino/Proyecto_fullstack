package producto.producto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El id de restaurante no puede estar vacío")
    @Column(name = "id_restaurante_ref", nullable = false)
    private Integer idRestauranteRef;

    @NotNull(message = "El id de categoría no puede estar nulo")
    @Column(name = "id_categoria_ref", nullable = false)
    private Integer idCategoriaRef;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 300)
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "La disponibilidad no puede ser nula")
    @Column(nullable = false)
    private Boolean disponible;
}
