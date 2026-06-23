package com.pagos.pagos.controller;

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

import com.pagos.pagos.dto.PagoDetalleDTO;
import com.pagos.pagos.dto.PagoListadoDTO;
import com.pagos.pagos.model.Pago;
import com.pagos.pagos.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos de Click & Eat")
public class PagoController {

    @Autowired
    private PagoService service;


    @Operation(summary = "Listar pagos", description = "Obtiene la lista de todos los pagos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<PagoListadoDTO>> listarPagos() {
        return ResponseEntity.ok(service.getPagosListado());
    }


    @Operation(summary = "Buscar pago por ID", description = "Obtiene el detalle completo de un pago por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDetalleDTO> buscarPago(@PathVariable Integer id) {
        return service.getPagoDetalle(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Crear pago", description = "Registra un nuevo pago verificando que el usuario y el pedido existan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del pago inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario o pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Pago> agregarPago(@RequestBody @Valid Pago pago) {
        Pago nuevo = service.addPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }


    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Integer id, @RequestBody @Valid Pago pago) {
        return service.updatePago(id, pago)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }



    @Operation(summary = "Eliminar pago", description = "Elimina un pago del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Integer id) {
        if (service.deletePago(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
