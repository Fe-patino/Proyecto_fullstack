package com.carrito.carrito.service;

import com.carrito.carrito.dto.CarritoItemRequestDTO;
import com.carrito.carrito.dto.CarritoItemResponseDTO;
import com.carrito.carrito.dto.CarritoResumenDTO;
import com.carrito.carrito.model.CarritoItem;
import com.carrito.carrito.repository.CarritoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoItemRepository repository;

    private CarritoItemResponseDTO mapearAResponse(CarritoItem item) {
        return new CarritoItemResponseDTO(
            item.getId(),
            item.getUsuarioId(),
            item.getRestauranteId(),
            item.getNombreProducto(),
            item.getSkuProducto(),
            item.getDescripcion(),
            item.getCantidad(),
            item.getPrecioUnitario(),
            item.getSubtotal(),
            item.getEstado(),
            item.getFechaAgregado() != null ? item.getFechaAgregado().toString() : "Fecha pendiente",
            item.getFechaActualizacion() != null ? item.getFechaActualizacion().toString() : "Fecha pendiente"
        );
    }

    public CarritoItemResponseDTO agregarItem(CarritoItemRequestDTO dto) {
        Optional<CarritoItem> itemExistente = repository
                .findByUsuarioIdAndSkuProductoAndEstado(dto.usuarioId(), dto.skuProducto(), "ACTIVO");

        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + dto.cantidad());
            item.setFechaActualizacion(LocalDateTime.now());
            return mapearAResponse(repository.save(item));
        }

        CarritoItem nuevoItem = new CarritoItem();
        nuevoItem.setUsuarioId(dto.usuarioId());
        nuevoItem.setRestauranteId(dto.restauranteId());
        nuevoItem.setNombreProducto(dto.nombreProducto());
        nuevoItem.setSkuProducto(dto.skuProducto());
        nuevoItem.setDescripcion(dto.descripcion());
        nuevoItem.setCantidad(dto.cantidad());
        nuevoItem.setPrecioUnitario(dto.precioUnitario());

        return mapearAResponse(repository.save(nuevoItem));
    }

    public CarritoResumenDTO obtenerCarritoUsuario(Integer usuarioId) {
        List<CarritoItemResponseDTO> items = repository
                .findByUsuarioIdAndEstado(usuarioId, "ACTIVO")
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());

        Double total = repository.calcularTotalCarrito(usuarioId);
        if (total == null) total = 0.0;

        return new CarritoResumenDTO(usuarioId, items, items.size(), total);
    }

    public Optional<CarritoItemResponseDTO> obtenerItemPorId(Integer id) {
        return repository.findById(id)
                .map(this::mapearAResponse);
    }

    public Optional<CarritoItemResponseDTO> actualizarCantidad(Integer id, Integer nuevaCantidad) {
        return repository.findById(id)
                .map(item -> {
                    if (nuevaCantidad <= 0) {
                        item.setEstado("ELIMINADO");
                        item.setFechaActualizacion(LocalDateTime.now());
                    } else {
                        item.setCantidad(nuevaCantidad);
                        item.setFechaActualizacion(LocalDateTime.now());
                    }
                    return repository.save(item);
                })
                .map(this::mapearAResponse);
    }

    public boolean eliminarItem(Integer id) {
        return repository.findById(id)
                .map(item -> {
                    item.setEstado("ELIMINADO");
                    item.setFechaActualizacion(LocalDateTime.now());
                    repository.save(item);
                    return true;
                })
                .orElse(false);
    }

    public void vaciarCarrito(Integer usuarioId) {
        List<CarritoItem> itemsActivos = repository.findByUsuarioIdAndEstado(usuarioId, "ACTIVO");
        itemsActivos.forEach(item -> {
            item.setEstado("ELIMINADO");
            item.setFechaActualizacion(LocalDateTime.now());
        });
        repository.saveAll(itemsActivos);
    }

    public boolean confirmarCarrito(Integer usuarioId) {
        List<CarritoItem> itemsActivos = repository.findByUsuarioIdAndEstado(usuarioId, "ACTIVO");
        if (itemsActivos.isEmpty()) {
            return false;
        }
        itemsActivos.forEach(item -> {
            item.setEstado("CONFIRMADO");
            item.setFechaActualizacion(LocalDateTime.now());
        });
        repository.saveAll(itemsActivos);
        return true;
    }

    public List<CarritoItemResponseDTO> obtenerHistorialUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }
}
