package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.RestauranteRequestDTO;
import com.restaurante.restaurante.dto.RestauranteResponseDTO;
import com.restaurante.restaurante.model.Restaurante;
import com.restaurante.restaurante.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository repository;

    private RestauranteResponseDTO mapearAResponse(Restaurante r) {
        return new RestauranteResponseDTO(
            r.getId(), r.getNombre(), r.getDireccion(), 
            r.getTipoComida(), r.getHorario(), r.getCalificacion(), r.getAbierto()
        );
    }

    public List<RestauranteResponseDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Devolvemos Optional por si el ID no existe
    public Optional<RestauranteResponseDTO> buscarPorId(Integer id) {
        return repository.findById(id)
                .map(this::mapearAResponse);
    }

    public RestauranteResponseDTO guardar(RestauranteRequestDTO dto) {
        if (repository.existsByNombre(dto.nombre())) {
            throw new RuntimeException("El restaurante ya existe");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(dto.nombre());
        restaurante.setDireccion(dto.direccion());
        restaurante.setTipoComida(dto.tipoComida());
        restaurante.setHorario(dto.horario());
        restaurante.setCalificacion(dto.calificacion());
        restaurante.setAbierto(true);
        
        return mapearAResponse(repository.save(restaurante));
    }

    public Optional<RestauranteResponseDTO> cambiarEstado(Integer id, Boolean abierto) {
        return repository.findById(id)
                .map(restaurante -> {
                    restaurante.setAbierto(abierto);
                    return repository.save(restaurante);
                })
                .map(this::mapearAResponse);
    }
}