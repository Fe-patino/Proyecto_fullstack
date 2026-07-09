package com.carrito.carrito.controller;

import com.carrito.carrito.dto.CarritoResumenDTO;
import com.carrito.carrito.service.CarritoService;
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

@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService service;

    @Test
    void obtenerCarritoUsuario() throws Exception {
        CarritoResumenDTO resumen = new CarritoResumenDTO(1, List.of(), 0, 0.0);
        when(service.obtenerCarritoUsuario(1)).thenReturn(resumen);
        mockMvc.perform(get("/api/carrito/usuario/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerItemPorId() throws Exception {
        when(service.obtenerItemPorId(999)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/carrito/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerHistorialUsuario() throws Exception {
        when(service.obtenerHistorialUsuario(1)).thenReturn(List.of());
        mockMvc.perform(get("/api/carrito/usuario/1/historial"))
                .andExpect(status().isOk());
    }
}