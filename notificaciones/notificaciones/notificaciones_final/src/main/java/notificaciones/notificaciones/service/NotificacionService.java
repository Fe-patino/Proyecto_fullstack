package notificaciones.notificaciones.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import notificaciones.notificaciones.dto.NotificacionDetalleDTO;
import notificaciones.notificaciones.dto.NotificacionListadoDTO;
import notificaciones.notificaciones.dto.NotificacionResumenDTO;
import notificaciones.notificaciones.model.Notificacion;
import notificaciones.notificaciones.repository.NotificacionRepository;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository repository;
    private final RestTemplate restTemplate;

    private NotificacionDetalleDTO mapearAResponse(Notificacion n) {
        return new NotificacionDetalleDTO(
                n.getId(),
                n.getUsuarioId(),
                n.getTipoDestinatario(),
                n.getTipo(),
                n.getCanal(),
                n.getTitulo(),
                n.getMensaje(),
                n.getPedidoId(),
                n.getRestauranteId(),
                n.getLeida(),
                n.getFechaEnvio() != null ? n.getFechaEnvio().toString() : "Fecha pendiente"
        );
    }

    public NotificacionDetalleDTO enviar(NotificacionListadoDTO dto) {
        try {
            // ANTES: se usaba localhost:8080 que solo funciona en la misma máquina
            // restTemplate.getForObject("http://localhost:8080/api/v1/usuarios/" + dto.getUsuarioId(), Object.class);
            
            // DESPUÉS: se usa el nombre USUARIO registrado en Eureka
            // Eureka resuelve automáticamente la IP y puerto real del microservicio
            // Funciona en cualquier ambiente: local, Docker, Railway, etc.
            restTemplate.getForObject(
                "http://USUARIO/api/v1/usuarios/" + dto.getUsuarioId(),
                Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("El usuario con id " + dto.getUsuarioId() + " no existe");
        }

        Notificacion n = new Notificacion();
        n.setUsuarioId(dto.getUsuarioId());
        n.setTipoDestinatario(dto.getTipoDestinatario());
        n.setTipo(dto.getTipo());
        n.setCanal(dto.getCanal());
        n.setTitulo(dto.getTitulo());
        n.setMensaje(dto.getMensaje());
        n.setPedidoId(dto.getPedidoId());
        n.setRestauranteId(dto.getRestauranteId());
        return mapearAResponse(repository.save(n));
    }

    public NotificacionDetalleDTO notificarPedidoConfirmado(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "PEDIDO_CONFIRMADO",
                "PUSH", "Pedido confirmado",
                "Tu pedido #" + pedidoId + " fue confirmado y esta siendo preparado.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarRepartidorAsignado(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "REPARTIDOR_ASIGNADO",
                "PUSH", "Repartidor en camino",
                "Se asigno un repartidor a tu pedido #" + pedidoId + ".", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPedidoEnCamino(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "PEDIDO_EN_CAMINO",
                "PUSH", "Tu pedido va en camino",
                "Tu pedido #" + pedidoId + " ya fue retirado y esta en camino.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPedidoEntregado(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "PEDIDO_ENTREGADO",
                "PUSH", "Pedido entregado",
                "Tu pedido #" + pedidoId + " fue entregado. Calificalo en la app.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPagoAprobado(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "PAGO_APROBADO",
                "EMAIL", "Pago aprobado",
                "El pago de tu pedido #" + pedidoId + " fue aprobado correctamente.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPagoRechazado(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "PAGO_RECHAZADO",
                "EMAIL", "Pago rechazado",
                "El pago de tu pedido #" + pedidoId + " fue rechazado. Intenta con otro metodo.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPedidoCanceladoCliente(Integer usuarioId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "PEDIDO_CANCELADO",
                "PUSH", "Pedido cancelado",
                "Tu pedido #" + pedidoId + " fue cancelado.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarReseniaRecibida(Integer usuarioId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(usuarioId, "CLIENTE", "RESENIA_RECIBIDA",
                "PUSH", "Resenia guardada",
                "Tu resenia fue registrada correctamente. Gracias por tu opinion.", null, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarNuevoPedido(Integer restauranteId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(restauranteId, "RESTAURANTE", "NUEVO_PEDIDO",
                "PUSH", "Nuevo pedido recibido",
                "Tienes un nuevo pedido #" + pedidoId + " esperando preparacion.", pedidoId, restauranteId);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPedidoCanceladoRestaurante(Integer restauranteId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(restauranteId, "RESTAURANTE", "PEDIDO_CANCELADO",
                "PUSH", "Pedido cancelado",
                "El pedido #" + pedidoId + " fue cancelado por el cliente.", pedidoId, restauranteId);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarReseniaNueva(Integer restauranteId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(restauranteId, "RESTAURANTE", "RESENIA_NUEVA",
                "EMAIL", "Nueva resenia recibida",
                "Un cliente dejo una nueva resenia para tu restaurante.", null, restauranteId);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPedidoAsignadoRepartidor(Integer repartidorId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(repartidorId, "REPARTIDOR", "PEDIDO_ASIGNADO",
                "PUSH", "Nuevo pedido asignado",
                "Se te asigno el pedido #" + pedidoId + ". Dirigete al restaurante.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPedidoCanceladoRepartidor(Integer repartidorId, Integer pedidoId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(repartidorId, "REPARTIDOR", "PEDIDO_CANCELADO",
                "PUSH", "Pedido cancelado",
                "El pedido #" + pedidoId + " fue cancelado.", pedidoId, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarNuevaZonaDisponible(Integer repartidorId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(repartidorId, "REPARTIDOR", "NUEVA_ZONA_DISPONIBLE",
                "PUSH", "Pedidos disponibles cerca",
                "Hay pedidos disponibles en tu zona. Activa tu disponibilidad.", null, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarPagoFallidoRepetido(Integer adminId, Integer usuarioId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(adminId, "ADMIN", "PAGO_FALLIDO_REPETIDO",
                "EMAIL", "Alerta: pagos fallidos repetidos",
                "El usuario #" + usuarioId + " ha tenido multiples intentos de pago fallidos.", null, null);
        return enviar(dto);
    }

    public NotificacionDetalleDTO notificarSinRepartidores(Integer adminId) {
        NotificacionListadoDTO dto = new NotificacionListadoDTO(adminId, "ADMIN", "SIN_REPARTIDORES_DISPONIBLES",
                "EMAIL", "Alerta: sin repartidores disponibles",
                "No hay repartidores disponibles en este momento. Revisa la plataforma.", null, null);
        return enviar(dto);
    }

    public List<NotificacionDetalleDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<NotificacionDetalleDTO> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::mapearAResponse);
    }

    public List<NotificacionDetalleDTO> obtenerPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public List<NotificacionDetalleDTO> obtenerNoLeidasPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioIdAndLeida(usuarioId, false).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public List<NotificacionDetalleDTO> obtenerPorPedido(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public List<NotificacionDetalleDTO> obtenerPorTipoDestinatario(String tipoDestinatario) {
        return repository.findByTipoDestinatario(tipoDestinatario).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<NotificacionDetalleDTO> marcarLeida(Integer id) {
        return repository.findById(id).map(n -> {
            n.setLeida(true);
            return mapearAResponse(repository.save(n));
        });
    }

    public void marcarTodasLeidas(Integer usuarioId) {
        List<Notificacion> noLeidas = repository.findByUsuarioIdAndLeida(usuarioId, false);
        noLeidas.forEach(n -> n.setLeida(true));
        repository.saveAll(noLeidas);
    }

    public NotificacionResumenDTO resumenUsuario(Integer usuarioId) {
        return new NotificacionResumenDTO(
                usuarioId,
                repository.countByUsuarioId(usuarioId),
                repository.countByUsuarioIdAndLeida(usuarioId, false)
        );
    }

    public boolean verificarPagosFallidos(Integer usuarioId) {
        return repository.contarPagosFallidos(usuarioId) >= 3;
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}