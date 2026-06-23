package notificaciones.notificaciones.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notificaciones.notificaciones.dto.NotificacionDetalleDTO;
import notificaciones.notificaciones.dto.NotificacionListadoDTO;
import notificaciones.notificaciones.dto.NotificacionResumenDTO;
import notificaciones.notificaciones.service.NotificacionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    // POST: enviar notificacion generica
    @PostMapping
    public ResponseEntity<NotificacionDetalleDTO> enviar(@Valid @RequestBody NotificacionListadoDTO dto) {
        return new ResponseEntity<>(service.enviar(dto), HttpStatus.CREATED);
    }

    // ── CLIENTE ───────────────────────────────────────────────────────────

    @PostMapping("/cliente/{usuarioId}/pedido-confirmado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoConfirmado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoConfirmado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/repartidor-asignado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> repartidorAsignado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarRepartidorAsignado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/pedido-en-camino/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoEnCamino(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoEnCamino(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/pedido-entregado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoEntregado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoEntregado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/pago-aprobado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pagoAprobado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPagoAprobado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/pago-rechazado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pagoRechazado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPagoRechazado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/pedido-cancelado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoCanceladoCliente(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoCanceladoCliente(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/cliente/{usuarioId}/resenia-recibida")
    public ResponseEntity<NotificacionDetalleDTO> reseniaRecibida(@PathVariable Integer usuarioId) {
        return new ResponseEntity<>(service.notificarReseniaRecibida(usuarioId), HttpStatus.CREATED);
    }

    // ── RESTAURANTE ───────────────────────────────────────────────────────

    @PostMapping("/restaurante/{restauranteId}/nuevo-pedido/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> nuevoPedido(
            @PathVariable Integer restauranteId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarNuevoPedido(restauranteId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/restaurante/{restauranteId}/pedido-cancelado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoCanceladoRestaurante(
            @PathVariable Integer restauranteId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoCanceladoRestaurante(restauranteId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/restaurante/{restauranteId}/resenia-nueva")
    public ResponseEntity<NotificacionDetalleDTO> reseniaNueva(@PathVariable Integer restauranteId) {
        return new ResponseEntity<>(service.notificarReseniaNueva(restauranteId), HttpStatus.CREATED);
    }

    // ── REPARTIDOR ────────────────────────────────────────────────────────

    @PostMapping("/repartidor/{repartidorId}/pedido-asignado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoAsignadoRepartidor(
            @PathVariable Integer repartidorId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoAsignadoRepartidor(repartidorId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/repartidor/{repartidorId}/pedido-cancelado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoCanceladoRepartidor(
            @PathVariable Integer repartidorId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoCanceladoRepartidor(repartidorId, pedidoId), HttpStatus.CREATED);
    }

    @PostMapping("/repartidor/{repartidorId}/nueva-zona")
    public ResponseEntity<NotificacionDetalleDTO> nuevaZonaDisponible(@PathVariable Integer repartidorId) {
        return new ResponseEntity<>(service.notificarNuevaZonaDisponible(repartidorId), HttpStatus.CREATED);
    }

    // ── ADMIN ─────────────────────────────────────────────────────────────

    @PostMapping("/admin/{adminId}/pago-fallido/{usuarioId}")
    public ResponseEntity<NotificacionDetalleDTO> pagoFallidoRepetido(
            @PathVariable Integer adminId, @PathVariable Integer usuarioId) {
        return new ResponseEntity<>(service.notificarPagoFallidoRepetido(adminId, usuarioId), HttpStatus.CREATED);
    }

    @PostMapping("/admin/{adminId}/sin-repartidores")
    public ResponseEntity<NotificacionDetalleDTO> sinRepartidores(@PathVariable Integer adminId) {
        return new ResponseEntity<>(service.notificarSinRepartidores(adminId), HttpStatus.CREATED);
    }

    // ── CONSULTAS ─────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<NotificacionDetalleDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDetalleDTO> obtenerUno(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionDetalleDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDetalleDTO>> obtenerNoLeidas(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerNoLeidasPorUsuario(usuarioId));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<NotificacionDetalleDTO>> obtenerPorPedido(@PathVariable Integer pedidoId) {
        return ResponseEntity.ok(service.obtenerPorPedido(pedidoId));
    }

    @GetMapping("/tipo/{tipoDestinatario}")
    public ResponseEntity<List<NotificacionDetalleDTO>> obtenerPorTipo(@PathVariable String tipoDestinatario) {
        return ResponseEntity.ok(service.obtenerPorTipoDestinatario(tipoDestinatario));
    }

    @GetMapping("/usuario/{usuarioId}/resumen")
    public ResponseEntity<NotificacionResumenDTO> resumenUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.resumenUsuario(usuarioId));
    }

    // PATCH: marcar una notificacion como leida
    @PatchMapping("/{id}/leida")
    public ResponseEntity<NotificacionDetalleDTO> marcarLeida(@PathVariable Integer id) {
        return service.marcarLeida(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH: marcar todas las notificaciones de un usuario como leidas
    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasLeidas(@PathVariable Integer usuarioId) {
        service.marcarTodasLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    // DELETE: eliminar notificacion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
