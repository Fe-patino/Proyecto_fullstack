package com.productos.productos.Service;

import com.productos.productos.Model.producto;
import com.productos.productos.Repository.ProductoRepository;
import com.productos.productos.dto.ProductoDetalleDTO;
import com.productos.productos.dto.ProductoListadoDTO;
import com.productos.productos.dto.ProductoSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    // Repositorio para acceder a la base de datos

    // ────────────────────────────────────────────────────────────────────
    // Metodos con DTO
    // ────────────────────────────────────────────────────────────────────

    // GET: listar productos usando DTO (respuesta simplificada)
    public List<ProductoListadoDTO> listarDTO() {
        List<producto> productos = productoRepository.findAll(); // Se obtienen todas las entidades desde la base de datos
        List<ProductoListadoDTO> lista = new ArrayList<>();      // Se crea la lista que contendra los DTOs

        for (producto p : productos) {                           // Se recorre cada entidad para transformarla en un DTO
            ProductoListadoDTO dto = new ProductoListadoDTO();   // Se crea una nueva instancia del DTO
            dto.setId(p.getId());                                // Se copian solo los datos necesarios desde la entidad al DTO
            dto.setNombre(p.getNombre());
            dto.setPrecio(p.getPrecio());
            dto.setDisponible(p.getDisponible());
            lista.add(dto);                                      // Se agrega el DTO a la lista de resultados
        }

        return lista; // Se retorna la lista de DTOs en lugar de las entidades
    }

    // GET: buscar detalle de un producto por id usando DTO (respuesta completa)
    public Optional<ProductoDetalleDTO> buscarDetalleDTO(Long id) {
        Optional<producto> existente = productoRepository.findById(id);

        if (existente.isPresent()) {
            producto p = existente.get();
            ProductoDetalleDTO dto = new ProductoDetalleDTO();   // Se crea una nueva instancia del DTO
            dto.setId(p.getId());                                // Se copian todos los datos de la entidad al DTO
            dto.setIdRestauranteRef(p.getIdRestauranteRef());
            dto.setIdCategoriaRef(p.getIdCategoriaRef());
            dto.setNombre(p.getNombre());
            dto.setDescripcion(p.getDescripcion());
            dto.setPrecio(p.getPrecio());
            dto.setDisponible(p.getDisponible());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    // GET: buscar productos por restaurante usando DTO simple
    public List<ProductoSimpleDTO> buscarSimplesPorRestaurante(Long idRestauranteRef) {
        List<producto> productos = productoRepository.findByIdRestauranteRef(idRestauranteRef);
        List<ProductoSimpleDTO> lista = new ArrayList<>();

        for (producto p : productos) {
            ProductoSimpleDTO dto = new ProductoSimpleDTO();     // Se crea una nueva instancia del DTO simple
            dto.setId(p.getId());                                // Se copian solo los datos minimos necesarios
            dto.setNombre(p.getNombre());
            lista.add(dto);
        }

        return lista;
    }

    // ────────────────────────────────────────────────────────────────────
    // Metodos CRUD normales
    // ────────────────────────────────────────────────────────────────────

    // Listar todos los productos
    public List<producto> listar() {
        return productoRepository.findAll();
    }

    // Buscar por id
    public Optional<producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Buscar por nombre
    public Optional<producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreIgnoreCase(nombre);
    }

    // Buscar por nombre que contenga una palabra
    public List<producto> buscarPorNombreContiene(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar por restaurante
    public List<producto> buscarPorRestaurante(Long idRestauranteRef) {
        return productoRepository.findByIdRestauranteRef(idRestauranteRef);
    }

    // Buscar por categoria
    public List<producto> buscarPorCategoria(Long idCategoriaRef) {
        return productoRepository.findByIdCategoriaRef(idCategoriaRef);
    }

    // Buscar por disponibilidad
    public List<producto> buscarPorDisponible(Boolean disponible) {
        return productoRepository.findByDisponible(disponible);
    }

    // Buscar disponibles de un restaurante
    public List<producto> buscarDisponiblesPorRestaurante(Long idRestauranteRef) {
        return productoRepository.findByIdRestauranteRefAndDisponible(idRestauranteRef, true);
    }

    // Guardar producto
    public producto guardarProducto(producto p) {
        return productoRepository.save(p);
    }

    // Actualizar producto por id
    public String actualizarProducto(Long id, producto productoNuevo) {
        Optional<producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            producto p = existente.get();
            p.setNombre(productoNuevo.getNombre());
            p.setDescripcion(productoNuevo.getDescripcion());
            p.setPrecio(productoNuevo.getPrecio());
            p.setIdCategoriaRef(productoNuevo.getIdCategoriaRef());
            p.setDisponible(productoNuevo.getDisponible());
            productoRepository.save(p);
            return "Producto actualizado correctamente";
        } else {
            return "Producto no encontrado con id: " + id;
        }
    }

    // Activar producto
    public String activarProducto(Long id) {
        Optional<producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            producto p = existente.get();
            p.setDisponible(true);
            productoRepository.save(p);
            return "Producto activado correctamente";
        } else {
            return "Producto no encontrado con id: " + id;
        }
    }

    // Desactivar producto
    public String desactivarProducto(Long id) {
        Optional<producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            producto p = existente.get();
            p.setDisponible(false);
            productoRepository.save(p);
            return "Producto desactivado correctamente";
        } else {
            return "Producto no encontrado con id: " + id;
        }
    }

    // Eliminar por id
    public String eliminarPorId(Long id) {
        Optional<producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            productoRepository.deleteById(id);
            return "Producto eliminado correctamente";
        } else {
            return "Producto no encontrado con id: " + id;
        }
    }

}
