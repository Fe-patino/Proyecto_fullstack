package com.productos.productos.Repository;

import com.productos.productos.Model.producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<producto, Long> {

    List<producto> findByIdRestauranteRef(Long idRestauranteRef);

    Optional<producto> findByNombreIgnoreCase(String nombre);

    List<producto> findByNombreContainingIgnoreCase(String nombre);

    List<producto> findByIdCategoriaRef(Long idCategoriaRef);

    List<producto> findByDisponible(Boolean disponible);

    List<producto> findByIdRestauranteRefAndDisponible(Long idRestauranteRef, Boolean disponible);

}
