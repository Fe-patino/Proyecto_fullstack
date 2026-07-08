package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.RestauranteRequestDTO;
import com.restaurante.restaurante.dto.RestauranteResponseDTO;
import com.restaurante.restaurante.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar restaurantes", description = "Obtiene todos los restaurantes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<RestauranteResponseDTO> listar() {
        return service.obtenerTodos();
    }

    @Operation(summary = "Buscar restaurante por id", description = "Obtiene el detalle de un restaurante por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> obtenerUno(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear restaurante", description = "Registra un nuevo restaurante validando que el nombre no exista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado correctamente"),
            @ApiResponse(responseCode = "400", description = "El restaurante ya existe o datos inválidos")
    })
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> crear(@Valid @RequestBody RestauranteRequestDTO dto) {
        try {
            return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Cambiar estado del restaurante", description = "Abre o cierra un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<RestauranteResponseDTO> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam Boolean abierto) {
        return service.cambiarEstado(id, abierto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}