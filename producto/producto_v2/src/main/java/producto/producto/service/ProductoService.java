package producto.producto.service;

import producto.producto.dto.ProductoListadoDTO;
import producto.producto.dto.ProductoSimpleDTO;
import producto.producto.dto.ProductoDetalleDTO;
import producto.producto.model.Producto;
import producto.producto.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;
    private final RestTemplate restTemplate;

    public List<ProductoListadoDTO> listarDTO() {
        return repository.findAll().stream()
                .map(p -> new ProductoListadoDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getDisponible()))
                .collect(Collectors.toList());
    }

    public Optional<ProductoDetalleDTO> buscarDetalleDTO(Integer id) {
        return repository.findById(id).map(p -> new ProductoDetalleDTO(
                p.getId(), p.getIdRestauranteRef(), p.getIdCategoriaRef(),
                p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getDisponible()
        ));
    }

    public List<ProductoSimpleDTO> buscarSimplesPorRestaurante(Integer idRestauranteRef) {
        return repository.findByIdRestauranteRef(idRestauranteRef).stream()
                .map(p -> new ProductoSimpleDTO(p.getId(), p.getNombre()))
                .collect(Collectors.toList());
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Optional<Producto> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Optional<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }

    public List<Producto> buscarPorNombreContiene(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorRestaurante(Integer idRestauranteRef) {
        return repository.findByIdRestauranteRef(idRestauranteRef);
    }

    public List<Producto> buscarPorCategoria(Integer idCategoriaRef) {
        return repository.findByIdCategoriaRef(idCategoriaRef);
    }

    public List<Producto> buscarPorDisponible(Boolean disponible) {
        return repository.findByDisponible(disponible);
    }

    public List<Producto> buscarDisponiblesPorRestaurante(Integer idRestauranteRef) {
        return repository.findByIdRestauranteRefAndDisponible(idRestauranteRef, true);
    }

    public ProductoDetalleDTO guardar(Producto p) {

        // Verifica restaurante — usa nombre de Eureka
        try {
            restTemplate.getForObject(
                "http://RESTAURANTE/api/restaurantes/" + p.getIdRestauranteRef(),
                Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("El restaurante con id " + p.getIdRestauranteRef() + " no existe");
        }

        // Verifica categoría — usa nombre de Eureka
        try {
            restTemplate.getForObject(
                "http://CATEGORIAS/api/categorias/" + p.getIdCategoriaRef(),
                Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("La categoría con id " + p.getIdCategoriaRef() + " no existe");
        }

        Producto guardado = repository.save(p);
        return new ProductoDetalleDTO(
                guardado.getId(), guardado.getIdRestauranteRef(), guardado.getIdCategoriaRef(),
                guardado.getNombre(), guardado.getDescripcion(), guardado.getPrecio(), guardado.getDisponible()
        );
    }

    public Optional<ProductoDetalleDTO> actualizar(Integer id, Producto nuevo) {
        return repository.findById(id).map(p -> {
            p.setNombre(nuevo.getNombre());
            p.setDescripcion(nuevo.getDescripcion());
            p.setPrecio(nuevo.getPrecio());
            p.setIdCategoriaRef(nuevo.getIdCategoriaRef());
            p.setDisponible(nuevo.getDisponible());
            Producto guardado = repository.save(p);
            return new ProductoDetalleDTO(
                    guardado.getId(), guardado.getIdRestauranteRef(), guardado.getIdCategoriaRef(),
                    guardado.getNombre(), guardado.getDescripcion(), guardado.getPrecio(), guardado.getDisponible()
            );
        });
    }

    public Optional<ProductoDetalleDTO> cambiarDisponibilidad(Integer id, Boolean disponible) {
        return repository.findById(id).map(p -> {
            p.setDisponible(disponible);
            Producto guardado = repository.save(p);
            return new ProductoDetalleDTO(
                    guardado.getId(), guardado.getIdRestauranteRef(), guardado.getIdCategoriaRef(),
                    guardado.getNombre(), guardado.getDescripcion(), guardado.getPrecio(), guardado.getDisponible()
            );
        });
    }

    public boolean eliminar(Integer id) {
        return repository.findById(id).map(p -> {
            repository.delete(p);
            return true;
        }).orElse(false);
    }
}