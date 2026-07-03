package resenas.resenas.controller;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import resenas.resenas.dto.ErrorDTO;
import resenas.resenas.dto.ReseniaDetalleDTO;
import resenas.resenas.dto.ReseniaListadoDTO;
import resenas.resenas.dto.ReseniaResumenDTO;
import resenas.resenas.service.ReseniaService;

@RestController
@RequestMapping("/api/resenias")
@RequiredArgsConstructor
public class ReseniaController {

    private final ReseniaService service;

    // POST: crear reseña
    @Operation(
            summary = "Crear resenia",
            description = "Registra una resenia para un pedido, validando en ms-pedidos que el pedido " +
                    "ya fue entregado y que no exista una resenia previa para ese pedido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resenia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "El pedido no fue entregado, ya tiene una resenia o los datos son invalidos")
    })
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ReseniaListadoDTO dto) {
        return service.crearResenia(dto)
                .<ResponseEntity<?>>map(r -> new ResponseEntity<>(r, HttpStatus.CREATED))
                .orElse(ResponseEntity.status(400).body(
                    new ErrorDTO(LocalDateTime.now(), 400,
                        "No se pudo crear la resenia. El pedido no fue entregado o ya tiene una resenia",
                        null, "/api/resenias")
                ));
    }

    // GET: listar todas
    @Operation(
            summary = "Listar resenias",
            description = "Obtiene todas las resenias registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay resenias registradas")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        List<ReseniaDetalleDTO> lista = service.obtenerTodas();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No hay resenias registradas en el sistema",
                    null, "/api/resenias")
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: buscar por id
    @Operation(
            summary = "Buscar resenia por id",
            description = "Obtiene el detalle de una resenia segun su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resenia encontrada"),
            @ApiResponse(responseCode = "404", description = "Resenia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Resenia no encontrada con id: " + id,
                        null, "/api/resenias/" + id)
                ));
    }

    // GET: buscar por pedido
    @Operation(
            summary = "Buscar resenia por pedido",
            description = "Obtiene la resenia asociada a un pedido especifico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resenia encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe una resenia para ese pedido")
    })
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> obtenerPorPedido(@PathVariable Integer pedidoId) {
        return service.obtenerPorPedido(pedidoId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "No existe una resenia para el pedido con id: " + pedidoId,
                        null, "/api/resenias/pedido/" + pedidoId)
                ));
    }

    // GET: reseñas de un usuario
    @Operation(
            summary = "Listar resenias de un usuario",
            description = "Obtiene todas las resenias creadas por un usuario especifico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron resenias para ese usuario")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        List<ReseniaDetalleDTO> lista = service.obtenerPorUsuario(usuarioId);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron resenias para el usuario con id: " + usuarioId,
                    null, "/api/resenias/usuario/" + usuarioId)
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: reseñas de un restaurante
    @Operation(
            summary = "Listar resenias de un restaurante",
            description = "Obtiene todas las resenias recibidas por un restaurante especifico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron resenias para ese restaurante")
    })
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<?> obtenerPorRestaurante(@PathVariable Integer restauranteId) {
        List<ReseniaDetalleDTO> lista = service.obtenerPorRestaurante(restauranteId);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron resenias para el restaurante con id: " + restauranteId,
                    null, "/api/resenias/restaurante/" + restauranteId)
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: resumen puntuaciones de un restaurante
    @Operation(
            summary = "Resumen de puntuaciones de un restaurante",
            description = "Obtiene el total de resenias y los promedios de puntuacion de un restaurante y sus repartidores"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumen obtenido correctamente")
    })
    @GetMapping("/restaurante/{restauranteId}/resumen")
    public ResponseEntity<ReseniaResumenDTO> resumenRestaurante(@PathVariable Integer restauranteId) {
        return ResponseEntity.ok(service.resumenRestaurante(restauranteId));
    }

    // DELETE: eliminar reseña
    @Operation(
            summary = "Eliminar resenia",
            description = "Elimina una resenia del sistema segun su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resenia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Resenia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok().body(
                new ErrorDTO(LocalDateTime.now(), 200,
                    "Resenia eliminada correctamente con id: " + id,
                    null, "/api/resenias/" + id)
            );
        }
        return ResponseEntity.status(404).body(
            new ErrorDTO(LocalDateTime.now(), 404,
                "Resenia no encontrada con id: " + id,
                null, "/api/resenias/" + id)
        );
    }
}