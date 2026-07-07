package resenas.resenas.service;

import lombok.RequiredArgsConstructor;
import resenas.resenas.dto.ReseniaDetalleDTO;
import resenas.resenas.dto.ReseniaListadoDTO;
import resenas.resenas.dto.ReseniaResumenDTO;
import resenas.resenas.model.Resenia;
import resenas.resenas.repository.ReseniaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReseniaService {

    private final ReseniaRepository repository;
    private final RestTemplate restTemplate;

    private ReseniaDetalleDTO mapearAResponse(Resenia r) {
        return new ReseniaDetalleDTO(
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

    private boolean pedidoEntregado(Integer pedidoId) {
        try {
            String url = "http://PEDIDOS/api/pedidos/" + pedidoId;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                String estado = (String) response.get("estado");
                return "ENTREGADO".equals(estado);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<ReseniaDetalleDTO> crearResenia(ReseniaListadoDTO dto) {
        if (!pedidoEntregado(dto.getPedidoId())) {
            return Optional.empty();
        }
        if (repository.existsByPedidoId(dto.getPedidoId())) {
            return Optional.empty();
        }
        Resenia r = new Resenia();
        r.setPedidoId(dto.getPedidoId());
        r.setUsuarioId(dto.getUsuarioId());
        r.setRestauranteId(dto.getRestauranteId());
        r.setPuntuacionRestaurante(dto.getPuntuacionRestaurante());
        r.setPuntuacionRepartidor(dto.getPuntuacionRepartidor());
        r.setComentario(dto.getComentario());
        return Optional.of(mapearAResponse(repository.save(r)));
    }

    public List<ReseniaDetalleDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<ReseniaDetalleDTO> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::mapearAResponse);
    }

    public Optional<ReseniaDetalleDTO> obtenerPorPedido(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId).map(this::mapearAResponse);
    }

    public List<ReseniaDetalleDTO> obtenerPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public List<ReseniaDetalleDTO> obtenerPorRestaurante(Integer restauranteId) {
        return repository.findByRestauranteId(restauranteId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public ReseniaResumenDTO resumenRestaurante(Integer restauranteId) {
        return new ReseniaResumenDTO(
            restauranteId,
            repository.countByRestauranteId(restauranteId),
            repository.promedioRestaurante(restauranteId),
            repository.promedioRepartidor(restauranteId)
        );
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}