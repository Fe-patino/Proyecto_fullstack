package com.carrito.carrito.repository;

import com.carrito.carrito.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {

    List<CarritoItem> findByUsuarioIdAndEstado(Integer usuarioId, String estado);

    List<CarritoItem> findByUsuarioId(Integer usuarioId);

    Optional<CarritoItem> findByUsuarioIdAndSkuProductoAndEstado(
            Integer usuarioId, String skuProducto, String estado);

    List<CarritoItem> findByUsuarioIdAndRestauranteIdAndEstado(
            Integer usuarioId, Integer restauranteId, String estado);

    @Query("SELECT SUM(c.cantidad * c.precioUnitario) FROM CarritoItem c " +
           "WHERE c.usuarioId = :usuarioId AND c.estado = 'ACTIVO'")
    Double calcularTotalCarrito(@Param("usuarioId") Integer usuarioId);

    long countByUsuarioIdAndEstado(Integer usuarioId, String estado);
}
