package com.resenias.resenias.service;

import com.resenias.resenias.client.PedidoClient;
import com.resenias.resenias.dto.ReseniaRequestDTO;
import com.resenias.resenias.dto.ReseniaResponseDTO;
import com.resenias.resenias.dto.ReseniaResumenDTO;
import com.resenias.resenias.model.Resenia;
import com.resenias.resenias.repository.ReseniaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReseniaService {

    private final ReseniaRepository repository;
    private final PedidoClient pedidoClient;

    // Convierte entidad a ResponseDTO
    private ReseniaResponseDTO mapearAResponse(Resenia r) {
        return new ReseniaResponseDTO(
            r.getId(),
            r.getPedidoId(),
            r.getUsuarioId(),
            r.getRestauranteId(),
            r.getPuntuacionRestaurante(),
            r.getPuntuacionRepartidor(),
            r.getComentario(),
            r.getFechaCreacion() != null ? r.getFechaCreacion().toString() : "Fecha pendiente"
        );
    }

    // Crear resenia — verifica que el pedido fue entregado en ms-pedidos
    public Optional<ReseniaResponseDTO> crearResenia(ReseniaRequestDTO dto) {
        // Verificar que el pedido ya fue entregado
        if (!pedidoClient.pedidoEntregado(dto.pedidoId())) {
            return Optional.empty();
        }
        // Verificar que no exista ya una resenia para este pedido
        if (repository.existsByPedidoId(dto.pedidoId())) {
            return Optional.empty();
        }
        Resenia r = new Resenia();
        r.setPedidoId(dto.pedidoId());
        r.setUsuarioId(dto.usuarioId());
        r.setRestauranteId(dto.restauranteId());
        r.setPuntuacionRestaurante(dto.puntuacionRestaurante());
        r.setPuntuacionRepartidor(dto.puntuacionRepartidor());
        r.setComentario(dto.comentario());
        return Optional.of(mapearAResponse(repository.save(r)));
    }

    // Listar todas
    public List<ReseniaResponseDTO> obtenerTodas() {
        return repository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<ReseniaResponseDTO> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::mapearAResponse);
    }

    // Buscar por pedido
    public Optional<ReseniaResponseDTO> obtenerPorPedido(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId).map(this::mapearAResponse);
    }

    // Buscar por usuario
    public List<ReseniaResponseDTO> obtenerPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por restaurante
    public List<ReseniaResponseDTO> obtenerPorRestaurante(Integer restauranteId) {
        return repository.findByRestauranteId(restauranteId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Resumen de puntuaciones de un restaurante
    public ReseniaResumenDTO resumenRestaurante(Integer restauranteId) {
        return new ReseniaResumenDTO(
            restauranteId,
            repository.countByRestauranteId(restauranteId),
            repository.promedioRestaurante(restauranteId),
            repository.promedioRepartidor(restauranteId)
        );
    }

    // Eliminar
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
