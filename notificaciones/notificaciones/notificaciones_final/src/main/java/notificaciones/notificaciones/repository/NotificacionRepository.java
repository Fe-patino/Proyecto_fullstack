package notificaciones.notificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import notificaciones.notificaciones.model.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    List<Notificacion> findByUsuarioId(Integer usuarioId);

    List<Notificacion> findByUsuarioIdAndLeida(Integer usuarioId, Boolean leida);

    List<Notificacion> findByTipoDestinatario(String tipoDestinatario);

    List<Notificacion> findByTipo(String tipo);

    List<Notificacion> findByPedidoId(Integer pedidoId);

    List<Notificacion> findByRestauranteId(Integer restauranteId);

    List<Notificacion> findByCanal(String canal);

    long countByUsuarioIdAndLeida(Integer usuarioId, Boolean leida);

    long countByUsuarioId(Integer usuarioId);

    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.usuarioId = :usuarioId AND n.tipo = 'PAGO_RECHAZADO'")
    long contarPagosFallidos(@Param("usuarioId") Integer usuarioId);
}
