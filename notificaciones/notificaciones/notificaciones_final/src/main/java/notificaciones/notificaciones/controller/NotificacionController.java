package notificaciones.notificaciones.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notificaciones.notificaciones.dto.ErrorDTO;
import notificaciones.notificaciones.dto.NotificacionDetalleDTO;
import notificaciones.notificaciones.dto.NotificacionListadoDTO;
import notificaciones.notificaciones.dto.NotificacionResumenDTO;
import notificaciones.notificaciones.service.NotificacionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    // POST: enviar notificacion generica
    @Operation(
            summary = "Enviar notificacion generica",
            description = "Crea y envia una notificacion personalizada a un usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada y enviada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping
    public ResponseEntity<NotificacionDetalleDTO> enviar(@Valid @RequestBody NotificacionListadoDTO dto) {
        return new ResponseEntity<>(service.enviar(dto), HttpStatus.CREATED);
    }

    // ── CLIENTE ───────────────────────────────────────────────────────────

    @Operation(summary = "Notificar pedido confirmado",
            description = "Envia una notificacion al cliente indicando que su pedido fue confirmado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/pedido-confirmado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoConfirmado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoConfirmado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar repartidor asignado",
            description = "Avisa al cliente que ya se le asigno un repartidor a su pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/repartidor-asignado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> repartidorAsignado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarRepartidorAsignado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pedido en camino",
            description = "Avisa al cliente que su pedido fue retirado y va en camino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/pedido-en-camino/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoEnCamino(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoEnCamino(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pedido entregado",
            description = "Avisa al cliente que su pedido fue entregado y lo invita a calificarlo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/pedido-entregado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoEntregado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoEntregado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pago aprobado",
            description = "Avisa al cliente que el pago de su pedido fue aprobado correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/pago-aprobado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pagoAprobado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPagoAprobado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pago rechazado",
            description = "Avisa al cliente que el pago de su pedido fue rechazado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/pago-rechazado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pagoRechazado(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPagoRechazado(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pedido cancelado al cliente",
            description = "Avisa al cliente que su pedido fue cancelado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/pedido-cancelado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoCanceladoCliente(
            @PathVariable Integer usuarioId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoCanceladoCliente(usuarioId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar resenia recibida",
            description = "Avisa al cliente que su resenia fue registrada correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o usuario inexistente")
    })
    @PostMapping("/cliente/{usuarioId}/resenia-recibida")
    public ResponseEntity<NotificacionDetalleDTO> reseniaRecibida(@PathVariable Integer usuarioId) {
        return new ResponseEntity<>(service.notificarReseniaRecibida(usuarioId), HttpStatus.CREATED);
    }

    // ── RESTAURANTE ───────────────────────────────────────────────────────

    @Operation(summary = "Notificar nuevo pedido al restaurante",
            description = "Avisa al restaurante que tiene un nuevo pedido esperando preparacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o restaurante inexistente")
    })
    @PostMapping("/restaurante/{restauranteId}/nuevo-pedido/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> nuevoPedido(
            @PathVariable Integer restauranteId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarNuevoPedido(restauranteId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pedido cancelado al restaurante",
            description = "Avisa al restaurante que un pedido fue cancelado por el cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o restaurante inexistente")
    })
    @PostMapping("/restaurante/{restauranteId}/pedido-cancelado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoCanceladoRestaurante(
            @PathVariable Integer restauranteId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoCanceladoRestaurante(restauranteId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar nueva resenia al restaurante",
            description = "Avisa al restaurante que un cliente dejo una nueva resenia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o restaurante inexistente")
    })
    @PostMapping("/restaurante/{restauranteId}/resenia-nueva")
    public ResponseEntity<NotificacionDetalleDTO> reseniaNueva(@PathVariable Integer restauranteId) {
        return new ResponseEntity<>(service.notificarReseniaNueva(restauranteId), HttpStatus.CREATED);
    }

    // ── REPARTIDOR ────────────────────────────────────────────────────────

    @Operation(summary = "Notificar pedido asignado al repartidor",
            description = "Avisa al repartidor que se le asigno un nuevo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o repartidor inexistente")
    })
    @PostMapping("/repartidor/{repartidorId}/pedido-asignado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoAsignadoRepartidor(
            @PathVariable Integer repartidorId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoAsignadoRepartidor(repartidorId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar pedido cancelado al repartidor",
            description = "Avisa al repartidor que el pedido asignado fue cancelado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o repartidor inexistente")
    })
    @PostMapping("/repartidor/{repartidorId}/pedido-cancelado/{pedidoId}")
    public ResponseEntity<NotificacionDetalleDTO> pedidoCanceladoRepartidor(
            @PathVariable Integer repartidorId, @PathVariable Integer pedidoId) {
        return new ResponseEntity<>(service.notificarPedidoCanceladoRepartidor(repartidorId, pedidoId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar nueva zona disponible",
            description = "Avisa al repartidor que hay pedidos disponibles cerca de su zona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o repartidor inexistente")
    })
    @PostMapping("/repartidor/{repartidorId}/nueva-zona")
    public ResponseEntity<NotificacionDetalleDTO> nuevaZonaDisponible(@PathVariable Integer repartidorId) {
        return new ResponseEntity<>(service.notificarNuevaZonaDisponible(repartidorId), HttpStatus.CREATED);
    }

    // ── ADMIN ─────────────────────────────────────────────────────────────

    @Operation(summary = "Notificar pagos fallidos repetidos",
            description = "Alerta al administrador sobre un usuario con multiples intentos de pago fallidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o administrador inexistente")
    })
    @PostMapping("/admin/{adminId}/pago-fallido/{usuarioId}")
    public ResponseEntity<NotificacionDetalleDTO> pagoFallidoRepetido(
            @PathVariable Integer adminId, @PathVariable Integer usuarioId) {
        return new ResponseEntity<>(service.notificarPagoFallidoRepetido(adminId, usuarioId), HttpStatus.CREATED);
    }

    @Operation(summary = "Notificar sin repartidores disponibles",
            description = "Alerta al administrador cuando no hay repartidores disponibles en la plataforma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o administrador inexistente")
    })
    @PostMapping("/admin/{adminId}/sin-repartidores")
    public ResponseEntity<NotificacionDetalleDTO> sinRepartidores(@PathVariable Integer adminId) {
        return new ResponseEntity<>(service.notificarSinRepartidores(adminId), HttpStatus.CREATED);
    }

    // ── CONSULTAS ─────────────────────────────────────────────────────────

    @Operation(summary = "Listar notificaciones",
            description = "Obtiene todas las notificaciones registradas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay notificaciones registradas")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        List<NotificacionDetalleDTO> lista = service.obtenerTodas();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No hay notificaciones registradas en el sistema",
                    null, "/api/notificaciones")
            );
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar notificacion por id",
            description = "Obtiene el detalle de una notificacion segun su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Notificacion no encontrada con id: " + id,
                        null, "/api/notificaciones/" + id)
                ));
    }

    @Operation(summary = "Listar notificaciones de un usuario",
            description = "Obtiene todas las notificaciones asociadas a un usuario especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron notificaciones para ese usuario")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        List<NotificacionDetalleDTO> lista = service.obtenerPorUsuario(usuarioId);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron notificaciones para el usuario con id: " + usuarioId,
                    null, "/api/notificaciones/usuario/" + usuarioId)
            );
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar notificaciones no leidas de un usuario",
            description = "Obtiene las notificaciones que un usuario aun no ha marcado como leidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay notificaciones no leidas para ese usuario")
    })
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<?> obtenerNoLeidas(@PathVariable Integer usuarioId) {
        List<NotificacionDetalleDTO> lista = service.obtenerNoLeidasPorUsuario(usuarioId);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No hay notificaciones no leidas para el usuario con id: " + usuarioId,
                    null, "/api/notificaciones/usuario/" + usuarioId + "/no-leidas")
            );
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar notificaciones de un pedido",
            description = "Obtiene todas las notificaciones generadas a partir de un pedido especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron notificaciones para ese pedido")
    })
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> obtenerPorPedido(@PathVariable Integer pedidoId) {
        List<NotificacionDetalleDTO> lista = service.obtenerPorPedido(pedidoId);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron notificaciones para el pedido con id: " + pedidoId,
                    null, "/api/notificaciones/pedido/" + pedidoId)
            );
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar notificaciones por tipo de destinatario",
            description = "Obtiene las notificaciones filtradas por tipo de destinatario (CLIENTE, RESTAURANTE, REPARTIDOR, ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron notificaciones para ese tipo de destinatario")
    })
    @GetMapping("/tipo/{tipoDestinatario}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable String tipoDestinatario) {
        List<NotificacionDetalleDTO> lista = service.obtenerPorTipoDestinatario(tipoDestinatario);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron notificaciones para el tipo de destinatario: " + tipoDestinatario,
                    null, "/api/notificaciones/tipo/" + tipoDestinatario)
            );
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Resumen de notificaciones de un usuario",
            description = "Obtiene el total de notificaciones y la cantidad de no leidas de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumen obtenido correctamente")
    })
    @GetMapping("/usuario/{usuarioId}/resumen")
    public ResponseEntity<NotificacionResumenDTO> resumenUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.resumenUsuario(usuarioId));
    }

    @Operation(summary = "Marcar notificacion como leida",
            description = "Cambia el estado de una notificacion especifica a leida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
    })
    @PatchMapping("/{id}/leida")
    public ResponseEntity<?> marcarLeida(@PathVariable Integer id) {
        return service.marcarLeida(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Notificacion no encontrada con id: " + id,
                        null, "/api/notificaciones/" + id + "/leida")
                ));
    }

    @Operation(summary = "Marcar todas las notificaciones de un usuario como leidas",
            description = "Cambia a leidas todas las notificaciones pendientes de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificaciones actualizadas correctamente")
    })
    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<?> marcarTodasLeidas(@PathVariable Integer usuarioId) {
        service.marcarTodasLeidas(usuarioId);
        return ResponseEntity.ok().body(
            new ErrorDTO(LocalDateTime.now(), 200,
                "Todas las notificaciones del usuario " + usuarioId + " fueron marcadas como leidas",
                null, "/api/notificaciones/usuario/" + usuarioId + "/marcar-todas-leidas")
        );
    }

    @Operation(summary = "Eliminar notificacion",
            description = "Elimina una notificacion del sistema segun su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok().body(
                new ErrorDTO(LocalDateTime.now(), 200,
                    "Notificacion eliminada correctamente con id: " + id,
                    null, "/api/notificaciones/" + id)
            );
        }
        return ResponseEntity.status(404).body(
            new ErrorDTO(LocalDateTime.now(), 404,
                "Notificacion no encontrada con id: " + id,
                null, "/api/notificaciones/" + id)
        );
    }
}