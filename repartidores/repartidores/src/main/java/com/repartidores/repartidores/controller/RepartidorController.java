package com.repartidores.repartidores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.repartidores.repartidores.dto.RepartidorDetalleDTO;
import com.repartidores.repartidores.dto.RepartidorListadoDTO;
import com.repartidores.repartidores.model.Repartidor;
import com.repartidores.repartidores.service.RepartidorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/repartidores") 
@Tag(name = "Repartidores", description = "Gestión de repartidores de Click & Eat")
public class RepartidorController {

    @Autowired
    private RepartidorService service;

    @Operation(summary = "Listar repartidores", description = "Obtiene la lista de todos los repartidores registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<RepartidorListadoDTO>> listarRepartidores() {
        return ResponseEntity.ok(service.getRepartidoresListado());
    }


    @Operation(summary = "Buscar repartidor por ID", description = "Obtiene el detalle completo de un repartidor por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Repartidor encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "Repartidor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RepartidorDetalleDTO> buscarRepartidor(@PathVariable Integer id) {
        return service.getRepartidorDetalle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear repartidor", description = "Registra un nuevo repartidor en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Repartidor creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del repartidor inválidos"),
        @ApiResponse(responseCode = "404", description = "Pedido asignado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Repartidor> agregarRepartidor(@RequestBody @Valid Repartidor repartidor) {
        Repartidor nuevo = service.addRepartidor(repartidor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    
    @Operation(summary = "Actualizar repartidor", description = "Actualiza los datos de un repartidor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Repartidor actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Repartidor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Repartidor> actualizarRepartidor(@PathVariable Integer id,
                                                            @RequestBody @Valid Repartidor repartidor) {
        return service.updateRepartidor(id, repartidor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @Operation(summary = "Eliminar repartidor", description = "Elimina un repartidor del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Repartidor eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Repartidor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRepartidor(@PathVariable Integer id) {
        if (service.deleteRepartidor(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
