package com.pedidos.pedidos.service;

import com.pedidos.pedidos.dto.PedidoRequestDTO;
import com.pedidos.pedidos.dto.PedidoResponseDTO;
import com.pedidos.pedidos.dto.HojaDespachoDTO;
import com.pedidos.pedidos.dto.RestauranteResponseDTO;
import com.pedidos.pedidos.dto.UsuarioDetalleDTO;
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

    public Optional<HojaDespachoDTO> obtenerHojaDespacho(Integer pedidoId) {
        return repository.findById(pedidoId).map(pedido -> {

            String nombreCliente = "Desconocido";
            String telefonoCliente = "No disponible";
            String direccionEntrega = "No disponible";

            String nombreRestaurante = "Desconocido";
            String direccionRestaurante = "No disponible";

            // 1. Consumir Datos del Usuario — usa nombre de Eureka
            try {
                // ANTES: http://localhost:8080/api/v1/usuarios/
                // DESPUÉS: http://USUARIO — Eureka resuelve la IP automáticamente
                String urlUsuario = "http://USUARIO/api/v1/usuarios/" + pedido.getUsuarioId();
                UsuarioDetalleDTO usuario = restTemplate.getForObject(urlUsuario, UsuarioDetalleDTO.class);

                if (usuario != null) {
                    nombreCliente = usuario.getNombreCompleto();
                    telefonoCliente = usuario.getTelefono();
                    direccionEntrega = usuario.getCalle() + ", " + usuario.getComuna() + ", " + usuario.getRegion();
                }
            } catch (Exception e) {
                nombreCliente = "Error al obtener datos del cliente";
            }

            // 2. Consumir Datos del Restaurante — usa nombre de Eureka
            try {
                // ANTES: http://localhost:8086/api/restaurantes/
                // DESPUÉS: http://RESTAURANTE — Eureka resuelve la IP automáticamente
                String urlRestaurante = "http://RESTAURANTE/api/restaurantes/" + pedido.getRestauranteId();
                RestauranteResponseDTO restaurante = restTemplate.getForObject(urlRestaurante, RestauranteResponseDTO.class);

                if (restaurante != null) {
                    nombreRestaurante = restaurante.getNombre();
                    direccionRestaurante = restaurante.getDireccion();
                }
            } catch (Exception e) {
                nombreRestaurante = "Error al obtener datos del restaurante";
            }

            return new HojaDespachoDTO(
                pedido.getId(),
                nombreCliente,
                telefonoCliente,
                direccionEntrega,
                nombreRestaurante,
                direccionRestaurante,
                pedido.getNombre(),
                pedido.getCantidad(),
                pedido.getEstado(),
                "Por favor llamar al cliente al llegar al domicilio."
            );
        });
    }

    public List<PedidoResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<PedidoResponseDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::mapearAResponse);
    }

    public PedidoResponseDTO guardar(PedidoRequestDTO dto) {
        try {
            // ANTES: localhost:8080 → DESPUÉS: nombre de Eureka USUARIO
            restTemplate.getForObject(
                "http://USUARIO/api/v1/usuarios/" + dto.usuarioId(),
                Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("El usuario con id " + dto.usuarioId() + " no existe");
        }

        try {
            // ANTES: localhost:8086 → DESPUÉS: nombre de Eureka RESTAURANTE
            restTemplate.getForObject(
                "http://RESTAURANTE/api/restaurantes/" + dto.restauranteId(),
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