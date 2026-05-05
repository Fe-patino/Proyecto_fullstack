package com.resenias.resenias.controller;

import com.resenias.resenias.dto.ReseniaRequestDTO;
import com.resenias.resenias.dto.ReseniaResponseDTO;
import com.resenias.resenias.dto.ReseniaResumenDTO;
import com.resenias.resenias.service.ReseniaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenias")
@RequiredArgsConstructor
public class ReseniaController {

    private final ReseniaService service;

    // POST: crear resenia (verifica pedido ENTREGADO en ms-pedidos)
    @PostMapping
    public ResponseEntity<ReseniaResponseDTO> crear(@Valid @RequestBody ReseniaRequestDTO dto) {
        return service.crearResenia(dto)
                .map(r -> new ResponseEntity<>(r, HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    // GET: listar todas
    @GetMapping
    public List<ReseniaResponseDTO> listar() {
        return service.obtenerTodas();
    }

    // GET: buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ReseniaResponseDTO> obtenerUno(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: buscar por pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<ReseniaResponseDTO> obtenerPorPedido(@PathVariable Integer pedidoId) {
        return service.obtenerPorPedido(pedidoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: resenias de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReseniaResponseDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    // GET: resenias de un restaurante
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ReseniaResponseDTO>> obtenerPorRestaurante(@PathVariable Integer restauranteId) {
        return ResponseEntity.ok(service.obtenerPorRestaurante(restauranteId));
    }

    // GET: resumen puntuaciones de un restaurante
    @GetMapping("/restaurante/{restauranteId}/resumen")
    public ResponseEntity<ReseniaResumenDTO> resumenRestaurante(@PathVariable Integer restauranteId) {
        return ResponseEntity.ok(service.resumenRestaurante(restauranteId));
    }

    // DELETE: eliminar resenia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
