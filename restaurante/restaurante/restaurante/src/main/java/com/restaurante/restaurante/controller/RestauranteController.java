package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.RestauranteRequestDTO;
import com.restaurante.restaurante.dto.RestauranteResponseDTO;
import com.restaurante.restaurante.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;

    @GetMapping
    public List<RestauranteResponseDTO> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> obtenerUno(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok) // Si hay valor, 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si está vacío, 404
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> crear(@Valid @RequestBody RestauranteRequestDTO dto) {
        try {
            return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RestauranteResponseDTO> actualizarEstado(
            @PathVariable Integer id, 
            @RequestParam Boolean abierto) {
        
        return service.cambiarEstado(id, abierto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}