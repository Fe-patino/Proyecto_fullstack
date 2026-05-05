package com.productos.productos.Controller;

import com.productos.productos.Model.producto;
import com.productos.productos.Service.ProductoService;
import com.productos.productos.dto.ProductoDetalleDTO;
import com.productos.productos.dto.ProductoListadoDTO;
import com.productos.productos.dto.ProductoSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;
    // Se inyecta el servicio para acceder a la logica del negocio

    // ────────────────────────────────────────────────────────────────────
    // Endpoints con DTO
    // ────────────────────────────────────────────────────────────────────

    // GET: listar productos usando DTO (respuesta simplificada)
    @GetMapping("/listar-dto")
    public List<ProductoListadoDTO> listarDTO() {
        return service.listarDTO();
    }

    // GET: buscar detalle completo de un producto por id usando DTO
    @GetMapping("/detalle/{id}")
    public Optional<ProductoDetalleDTO> buscarDetalleDTO(@PathVariable Long id) {
        return service.buscarDetalleDTO(id);
    }

    // GET: buscar productos simples por restaurante (para union de servicios)
    @GetMapping("/simple/restaurante/{idRestauranteRef}")
    public List<ProductoSimpleDTO> buscarSimplesPorRestaurante(@PathVariable Long idRestauranteRef) {
        return service.buscarSimplesPorRestaurante(idRestauranteRef);
    }

    // ────────────────────────────────────────────────────────────────────
    // Endpoints CRUD normales
    // ────────────────────────────────────────────────────────────────────

    // GET: listar todos los productos
    @GetMapping("/listar")
    public List<producto> listar() {
        return service.listar();
    }

    // GET: buscar por id
    @GetMapping("/id/{id}")
    public Optional<producto> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET: buscar por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public Optional<producto> buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre);
    }

    // GET: buscar por nombre que contenga una palabra
    @GetMapping("/buscar/{nombre}")
    public List<producto> buscarPorNombreContiene(@PathVariable String nombre) {
        return service.buscarPorNombreContiene(nombre);
    }

    // GET: buscar por restaurante
    @GetMapping("/restaurante/{idRestauranteRef}")
    public List<producto> buscarPorRestaurante(@PathVariable Long idRestauranteRef) {
        return service.buscarPorRestaurante(idRestauranteRef);
    }

    // GET: buscar por categoria
    @GetMapping("/categoria/{idCategoriaRef}")
    public List<producto> buscarPorCategoria(@PathVariable Long idCategoriaRef) {
        return service.buscarPorCategoria(idCategoriaRef);
    }

    // GET: buscar por disponibilidad
    @GetMapping("/disponible/{disponible}")
    public List<producto> buscarPorDisponible(@PathVariable Boolean disponible) {
        return service.buscarPorDisponible(disponible);
    }

    // GET: buscar disponibles de un restaurante
    @GetMapping("/restaurante/{idRestauranteRef}/disponibles")
    public List<producto> buscarDisponibles(@PathVariable Long idRestauranteRef) {
        return service.buscarDisponiblesPorRestaurante(idRestauranteRef);
    }

    // POST: agregar producto
    @PostMapping("/agregar")
    public producto agregar(@RequestBody producto p) {
        return service.guardarProducto(p);
    }

    // PUT: actualizar por id
    @PutMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody producto p) {
        Optional<producto> existente = service.buscarPorId(id);
        if (existente.isPresent()) {
            return service.actualizarProducto(id, p);
        } else {
            return "Producto no encontrado con id: " + id;
        }
    }

    // PUT: activar producto
    @PutMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        return service.activarProducto(id);
    }

    // PUT: desactivar producto
    @PutMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        return service.desactivarProducto(id);
    }

    // DELETE: eliminar por id
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Optional<producto> existente = service.buscarPorId(id);
        if (existente.isPresent()) {
            return service.eliminarPorId(id);
        } else {
            return "Producto no encontrado con id: " + id;
        }
    }

}
