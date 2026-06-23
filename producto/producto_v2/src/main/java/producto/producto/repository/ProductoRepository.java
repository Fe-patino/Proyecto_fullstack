package producto.producto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import producto.producto.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByIdRestauranteRef(Integer idRestauranteRef);

    Optional<Producto> findByNombreIgnoreCase(String nombre);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByIdCategoriaRef(Integer idCategoriaRef);

    List<Producto> findByDisponible(Boolean disponible);

    List<Producto> findByIdRestauranteRefAndDisponible(Integer idRestauranteRef, Boolean disponible);
}
