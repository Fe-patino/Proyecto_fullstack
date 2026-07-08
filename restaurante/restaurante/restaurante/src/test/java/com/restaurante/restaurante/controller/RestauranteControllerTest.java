package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.RestauranteResponseDTO;
import com.restaurante.restaurante.service.RestauranteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestauranteController.class)
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestauranteService service;

    @Test
    void listarRestaurantes() throws Exception {
        RestauranteResponseDTO r = new RestauranteResponseDTO(1, "Burger House", "Av. Principal 123", "Americana", "12:00-23:00", 4.5, true);
        when(service.obtenerTodos()).thenReturn(List.of(r));
        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarRestaurantePorId() throws Exception {
        RestauranteResponseDTO r = new RestauranteResponseDTO(1, "Burger House", "Av. Principal 123", "Americana", "12:00-23:00", 4.5, true);
        when(service.buscarPorId(1)).thenReturn(Optional.of(r));
        mockMvc.perform(get("/api/restaurantes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarRestauranteNoExistente() throws Exception {
        when(service.buscarPorId(999)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/restaurantes/999"))
                .andExpect(status().isNotFound());
    }
}