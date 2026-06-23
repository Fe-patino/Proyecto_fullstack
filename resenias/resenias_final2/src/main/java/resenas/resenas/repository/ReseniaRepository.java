package resenas.resenas.repository;

import resenas.resenas.model.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Integer> {

    // Un pedido solo puede tener una reseña
    Optional<Resenia> findByPedidoId(Integer pedidoId);

    // Todas las reseñas de un usuario
    List<Resenia> findByUsuarioId(Integer usuarioId);

    // Todas las reseñas de un restaurante
    List<Resenia> findByRestauranteId(Integer restauranteId);

    // Promedio puntuación restaurante
    @Query("SELECT AVG(r.puntuacionRestaurante) FROM Resenia r WHERE r.restauranteId = :restauranteId")
    Double promedioRestaurante(@Param("restauranteId") Integer restauranteId);

    // Promedio puntuación repartidor
    @Query("SELECT AVG(r.puntuacionRepartidor) FROM Resenia r WHERE r.restauranteId = :restauranteId AND r.puntuacionRepartidor IS NOT NULL")
    Double promedioRepartidor(@Param("restauranteId") Integer restauranteId);

    // Contar reseñas de un restaurante
    long countByRestauranteId(Integer restauranteId);

    // Verificar si ya existe una reseña para un pedido
    boolean existsByPedidoId(Integer pedidoId);
}