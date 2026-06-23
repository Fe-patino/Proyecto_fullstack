package com.pedidos.pedidos.service;

import com.pedidos.pedidos.dto.PedidoRequestDTO;
import com.pedidos.pedidos.dto.PedidoResponseDTO;
import com.pedidos.pedidos.model.Pedido;
import com.pedidos.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

    private final RestTemplate restTemplate;

    // Mapeo seguro de Entidad a ResponseDTO
    private PedidoResponseDTO mapearAResponse(Pedido p) {
        return new PedidoResponseDTO(
            p.getId(), 
            p.getUsuarioId(), 
            p.getRestauranteId(),
            p.getNombre(), 
            p.getSku(), 
            p.getDescripcion(),
            p.getCantidad(), 
            p.getPrecio(), 
            p.getEstado(),
            p.getFechaCreacion() != null ? p.getFechaCreacion().toString() : "Fecha pendiente"
        );
    }

    public List<PedidoResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Nuevo: Buscar un pedido específico de forma segura
    public Optional<PedidoResponseDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::mapearAResponse);
    }

    public PedidoResponseDTO guardar(PedidoRequestDTO dto) {
            // Verifica que el usuario existe en ms-usuarios (puerto 8080)
    try {
        restTemplate.getForObject(
            "http://localhost:8080/api/v1/usuarios/" + dto.usuarioId(),
            Object.class
        );
    } catch (Exception e) {
        throw new RuntimeException("El usuario con id " + dto.usuarioId() + " no existe");
    }

    // Verifica que el restaurante existe en ms-restaurante (puerto 8086)
    try {
        restTemplate.getForObject(
            "http://localhost:8086/api/restaurantes/" + dto.restauranteId(),
            Object.class
        );
    } catch (Exception e) {
        throw new RuntimeException("El restaurante con id " + dto.restauranteId() + " no existe");
    }
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(dto.usuarioId());
        pedido.setRestauranteId(dto.restauranteId());
        pedido.setNombre(dto.nombre());
        pedido.setSku(dto.sku());
        pedido.setDescripcion(dto.descripcion());
        pedido.setCantidad(dto.cantidad());
        pedido.setPrecio(dto.precio());
        
        return mapearAResponse(repository.save(pedido));
    }

    public Optional<PedidoResponseDTO> actualizarEstado(Integer id, String nuevoEstado) {
        return repository.findById(id)
                .map(pedido -> {
                    pedido.setEstado(nuevoEstado);
                    return repository.save(pedido);
                })
                .map(this::mapearAResponse);
    }

    public boolean eliminar(Integer id) {
        return repository.findById(id)
                .map(pedido -> {
                    repository.delete(pedido);
                    return true;
                })
                .orElse(false);
    }
}