package com.pedidos.pedidos.controller;

import com.pedidos.pedidos.dto.PedidoResponseDTO;
import com.pedidos.pedidos.service.PedidoService;
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

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService service;

    @Test
    void listarPedidos() throws Exception {
        PedidoResponseDTO pedido = new PedidoResponseDTO(
            1, 1, 1, "Hamburguesa", "BURG-001", "Sin cebolla",
            2.0, 5990.0, "PENDIENTE", "2026-07-07"
        );
        when(service.obtenerTodos()).thenReturn(List.of(pedido));
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPedidoPorId() throws Exception {
        PedidoResponseDTO pedido = new PedidoResponseDTO(
            1, 1, 1, "Hamburguesa", "BURG-001", "Sin cebolla",
            2.0, 5990.0, "PENDIENTE", "2026-07-07"
        );
        when(service.obtenerPorId(1)).thenReturn(Optional.of(pedido));
        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPedidoNoExistente() throws Exception {
        when(service.obtenerPorId(999)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/pedidos/999"))
                .andExpect(status().isNotFound());
    }
}