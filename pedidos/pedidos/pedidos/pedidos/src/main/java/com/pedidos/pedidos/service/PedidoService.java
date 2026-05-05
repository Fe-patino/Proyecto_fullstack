package com.pedidos.pedidos.service;

import com.pedidos.pedidos.dto.PedidoRequestDTO;
import com.pedidos.pedidos.dto.PedidoResponseDTO;
import com.pedidos.pedidos.model.Pedido;
import com.pedidos.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

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