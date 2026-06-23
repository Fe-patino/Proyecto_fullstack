package com.pedidos.pedidos.controller;

import com.pedidos.pedidos.dto.PedidoRequestDTO;
import com.pedidos.pedidos.dto.PedidoResponseDTO;
import com.pedidos.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    public List<PedidoResponseDTO> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerUno(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        // Al crear siempre esperamos éxito, por eso no usamos Optional aquí
        return new ResponseEntity<>(service.guardar(pedidoDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> cambiarEstado(
            @PathVariable Integer id, 
            @RequestParam String nuevoEstado) {
        
        return service.actualizarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id) 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.notFound().build();
    }
}