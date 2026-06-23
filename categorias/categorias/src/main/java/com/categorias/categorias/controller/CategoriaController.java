package com.categorias.categorias.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.categorias.categorias.dto.CategoriaDTO;
import com.categorias.categorias.dto.CategoriaResponseDTO;
import com.categorias.categorias.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Gestión de categorias de Click & Eat")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar categorias", description = "Obtiene la lista de todas las categorias registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodasLasCategorias());
    }

    @Operation(summary = "Listar categorias activas", description = "Obtiene la lista de todas las categorias registradas que se encuentran activas o disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorias obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarActivas() {
        return ResponseEntity.ok(categoriaService.obtenerCategoriasActivas());
    }

    @Operation(summary = "Buscar categoria por ID", description = "Obtiene el detalle completo de una categoria por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }

    @Operation(summary = "Crear categoria", description = "Registra una nueva categoria en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la categoria inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> guardar(@RequestBody CategoriaDTO categoriaDTO) {
        return new ResponseEntity<>(categoriaService.crearCategoria(categoriaDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar categoria", description = "Actualiza los datos de una categoria existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, categoriaDTO));
    }

    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Long id) {
        categoriaService.eliminarCategoriaLogica(id);
        return ResponseEntity.noContent().build();
    }
}
