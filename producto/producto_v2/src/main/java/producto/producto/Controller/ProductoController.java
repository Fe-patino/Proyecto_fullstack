package producto.producto.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import producto.producto.dto.ProductoListadoDTO;
import producto.producto.dto.ProductoSimpleDTO;
import producto.producto.dto.ErrorDTO;
import producto.producto.dto.ProductoDetalleDTO;
import producto.producto.service.ProductoService;
import producto.producto.model.Producto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    // GET: listar todos (listado simplificado)
    @Operation(
            summary = "Listar productos",
            description = "Obtiene el listado simplificado de todos los productos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay productos registrados")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        List<ProductoListadoDTO> lista = service.listarDTO();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No hay productos registrados en el sistema",
                    null, "/api/productos")
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: detalle completo por id
    @Operation(
            summary = "Buscar producto por id",
            description = "Obtiene el detalle completo de un producto segun su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Integer id) {
        return service.buscarDetalleDTO(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Producto no encontrado con id: " + id,
                        null, "/api/productos/" + id)
                ));
    }

    // GET: productos simples por restaurante
    @Operation(
            summary = "Listar productos simples de un restaurante",
            description = "Obtiene el id y nombre de los productos de un restaurante, pensado para ser consumido por ms-carrito"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos para ese restaurante")
    })
    @GetMapping("/restaurante/{idRestauranteRef}/simples")
    public ResponseEntity<?> simplesPorRestaurante(@PathVariable Integer idRestauranteRef) {
        List<ProductoSimpleDTO> lista = service.buscarSimplesPorRestaurante(idRestauranteRef);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron productos para el restaurante con id: " + idRestauranteRef,
                    null, "/api/productos/restaurante/" + idRestauranteRef + "/simples")
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: todos los productos de un restaurante
    @Operation(
            summary = "Listar productos de un restaurante",
            description = "Obtiene todos los productos asociados a un restaurante especifico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos para ese restaurante")
    })
    @GetMapping("/restaurante/{idRestauranteRef}")
    public ResponseEntity<?> porRestaurante(@PathVariable Integer idRestauranteRef) {
        List<ProductoListadoDTO> lista = service.buscarPorRestaurante(idRestauranteRef).stream()
                .map(p -> new ProductoListadoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getDisponible()))
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron productos para el restaurante con id: " + idRestauranteRef,
                    null, "/api/productos/restaurante/" + idRestauranteRef)
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: disponibles de un restaurante
    @Operation(
            summary = "Listar productos disponibles de un restaurante",
            description = "Obtiene los productos de un restaurante que se encuentran disponibles para la venta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay productos disponibles para ese restaurante")
    })
    @GetMapping("/restaurante/{idRestauranteRef}/disponibles")
    public ResponseEntity<?> disponiblesPorRestaurante(@PathVariable Integer idRestauranteRef) {
        List<ProductoListadoDTO> lista = service.buscarDisponiblesPorRestaurante(idRestauranteRef).stream()
                .map(p -> new ProductoListadoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getDisponible()))
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No hay productos disponibles para el restaurante con id: " + idRestauranteRef,
                    null, "/api/productos/restaurante/" + idRestauranteRef + "/disponibles")
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: buscar por nombre
    @Operation(
            summary = "Buscar productos por nombre",
            description = "Obtiene los productos cuyo nombre contiene el texto proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos con ese nombre")
    })
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        List<ProductoListadoDTO> lista = service.buscarPorNombreContiene(nombre).stream()
                .map(p -> new ProductoListadoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getDisponible()))
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron productos con el nombre: " + nombre,
                    null, "/api/productos/buscar/" + nombre)
            );
        }
        return ResponseEntity.ok(lista);
    }

    // GET: por categoría
    @Operation(
            summary = "Listar productos por categoria",
            description = "Obtiene los productos asociados a una categoria especifica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos para esa categoria")
    })
    @GetMapping("/categoria/{idCategoriaRef}")
    public ResponseEntity<?> porCategoria(@PathVariable Integer idCategoriaRef) {
        List<ProductoListadoDTO> lista = service.buscarPorCategoria(idCategoriaRef).stream()
                .map(p -> new ProductoListadoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getDisponible()))
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorDTO(LocalDateTime.now(), 404,
                    "No se encontraron productos para la categoria con id: " + idCategoriaRef,
                    null, "/api/productos/categoria/" + idCategoriaRef)
            );
        }
        return ResponseEntity.ok(lista);
    }

    // POST: crear producto
    @Operation(
            summary = "Crear producto",
            description = "Registra un nuevo producto, validando que el restaurante y la categoria asociados existan"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Restaurante o categoria inexistente")
    })
    @PostMapping
    public ResponseEntity<ProductoDetalleDTO> crear(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(service.guardar(producto), HttpStatus.CREATED);
    }

    // PUT: actualizar producto
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza los datos de un producto existente segun su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Producto producto) {
        return service.actualizar(id, producto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Producto no encontrado con id: " + id,
                        null, "/api/productos/" + id)
                ));
    }

    // PATCH: activar producto
    @Operation(
            summary = "Activar producto",
            description = "Marca un producto como disponible para la venta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto activado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Integer id) {
        return service.cambiarDisponibilidad(id, true)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Producto no encontrado con id: " + id,
                        null, "/api/productos/" + id + "/activar")
                ));
    }

    // PATCH: desactivar producto
    @Operation(
            summary = "Desactivar producto",
            description = "Marca un producto como no disponible para la venta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Integer id) {
        return service.cambiarDisponibilidad(id, false)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(
                    new ErrorDTO(LocalDateTime.now(), 404,
                        "Producto no encontrado con id: " + id,
                        null, "/api/productos/" + id + "/desactivar")
                ));
    }

    // DELETE: eliminar producto
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto del catalogo segun su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok().body(
                new ErrorDTO(LocalDateTime.now(), 200,
                    "Producto eliminado correctamente con id: " + id,
                    null, "/api/productos/" + id)
            );
        }
        return ResponseEntity.status(404).body(
            new ErrorDTO(LocalDateTime.now(), 404,
                "Producto no encontrado con id: " + id,
                null, "/api/productos/" + id)
        );
    }
}