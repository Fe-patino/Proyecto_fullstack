package com.carrito.carrito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Carritos", description = "Gestión de carritos de compras de Click & Eat")
public class CarritoController {

    private final CarritoService service;

    @Operation(summary = "Agregar item al carrito", description = "Registra un nuevo item en el carrito en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item agregado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del item inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CarritoItemResponseDTO> agregarItem(
            @Valid @RequestBody CarritoItemRequestDTO dto) {
        return new ResponseEntity<>(service.agregarItem(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar carrito por id de usuario", description = "Obtiene el detalle de un carrito por el id de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito encontrado correctamente"), // Corrección: 200 en lugar de 201
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoResumenDTO> obtenerCarrito(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerCarritoUsuario(usuarioId));
    }

    @Operation(summary = "Buscar item por id", description = "Obtiene el detalle de un item por el id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado correctamente"), // Corrección: 200 en lugar de 201
        @ApiResponse(responseCode = "404", description = "Item no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarritoItemResponseDTO> obtenerItem(@PathVariable Integer id) {
        return service.obtenerItemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar cantidad", description = "Actualiza la cantidad de un item existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/cantidad")
    public ResponseEntity<CarritoItemResponseDTO> actualizarCantidad(
            @PathVariable Integer id,
            @RequestParam Integer nuevaCantidad) {
        return service.actualizarCantidad(id, nuevaCantidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar item", description = "Elimina un item del carrito por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Integer id) {
        return service.eliminarItem(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Vaciar carrito", description = "Elimina todos los items de un carrito del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Carrito vaciado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/usuario/{usuarioId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Integer usuarioId) {
        service.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Confirmar carrito", description = "Confirma el carrito a través del id de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito confirmado correctamente"),
        @ApiResponse(responseCode = "404", description = "Carrito vacío o no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/usuario/{usuarioId}/confirmar")
    public ResponseEntity<Void> confirmarCarrito(@PathVariable Integer usuarioId) {
        return service.confirmarCarrito(usuarioId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar historial de carrito por ID de usuario", description = "Obtiene el historial completo de carritos por el ID de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial encontrado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}/historial")
    public ResponseEntity<List<CarritoItemResponseDTO>> obtenerHistorial(
            @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerHistorialUsuario(usuarioId));
    }
}
