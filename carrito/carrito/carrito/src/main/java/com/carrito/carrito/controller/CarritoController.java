package com.carrito.carrito.controller;

import com.carrito.carrito.dto.CarritoItemRequestDTO;
import com.carrito.carrito.dto.CarritoItemResponseDTO;
import com.carrito.carrito.dto.CarritoResumenDTO;
import com.carrito.carrito.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService service;

    @PostMapping
    public ResponseEntity<CarritoItemResponseDTO> agregarItem(
            @Valid @RequestBody CarritoItemRequestDTO dto) {
        return new ResponseEntity<>(service.agregarItem(dto), HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoResumenDTO> obtenerCarrito(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerCarritoUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoItemResponseDTO> obtenerItem(@PathVariable Integer id) {
        return service.obtenerItemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/cantidad")
    public ResponseEntity<CarritoItemResponseDTO> actualizarCantidad(
            @PathVariable Integer id,
            @RequestParam Integer nuevaCantidad) {
        return service.actualizarCantidad(id, nuevaCantidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Integer id) {
        return service.eliminarItem(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Integer usuarioId) {
        service.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/usuario/{usuarioId}/confirmar")
    public ResponseEntity<Void> confirmarCarrito(@PathVariable Integer usuarioId) {
        return service.confirmarCarrito(usuarioId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{usuarioId}/historial")
    public ResponseEntity<List<CarritoItemResponseDTO>> obtenerHistorial(
            @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerHistorialUsuario(usuarioId));
    }
}
