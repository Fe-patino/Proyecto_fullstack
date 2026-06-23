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

    // Convierte entidad a DetalleDTO
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

    // Verifica si un pedido fue entregado llamando a ms-pedidos
    private boolean pedidoEntregado(Integer pedidoId) {
        try {
            // Cliente HTTP para llamar al otro microservicio
            RestTemplate restTemplate = new RestTemplate();

            // URL del microservicio de pedidos
            String url = "http://localhost:8083/api/pedidos/" + pedidoId;

            // Llamada HTTP para obtener el pedido
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null) {
                String estado = (String) response.get("estado");
                return "ENTREGADO".equals(estado);
            }
            return false;
        } catch (Exception e) {
            // Si ms-pedidos no está disponible, no permitimos la reseña
            return false;
        }
    }

    // Crear reseña — verifica que el pedido fue entregado en ms-pedidos
    public Optional<ReseniaDetalleDTO> crearResenia(ReseniaListadoDTO dto) {
        // Verificar que el pedido ya fue entregado
        if (!pedidoEntregado(dto.getPedidoId())) {
            return Optional.empty();
        }
        // Verificar que no exista ya una reseña para este pedido
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

    // Listar todas
    public List<ReseniaDetalleDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<ReseniaDetalleDTO> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::mapearAResponse);
    }

    // Buscar por pedido
    public Optional<ReseniaDetalleDTO> obtenerPorPedido(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId).map(this::mapearAResponse);
    }

    // Buscar por usuario
    public List<ReseniaDetalleDTO> obtenerPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Buscar por restaurante
    public List<ReseniaDetalleDTO> obtenerPorRestaurante(Integer restauranteId) {
        return repository.findByRestauranteId(restauranteId).stream()
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