package com.pedidos.pedidos;

import com.pedidos.pedidos.controller.PedidoController;
import com.pedidos.pedidos.dto.PedidoResponseDTO;
import com.pedidos.pedidos.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService service;

    private PedidoResponseDTO pedidoResponseMock;

    @BeforeEach
    void setUp() {
        // Corregido: "PENDIENTE" con la 'N' correspondiente para evitar fallas de aserción
        pedidoResponseMock = new PedidoResponseDTO(
            1, 
            10, 
            20, 
            "Sushi Roll", 
            "SUSH-001-PROM", 
            "Promoción 40 piezas", 
            2.0, 
            12990.0, 
            "PENDIENTE", 
            "2026-06-23"
        );
    }

    @Test
    void cuandoListar_debeRetornarListaDePedidosYStatus200() throws Exception {
        // Configura el mock para retornar la lista tal como lo hace tu método listar()
        when(service.obtenerTodos()).thenReturn(Collections.singletonList(pedidoResponseMock));

        mockMvc.perform(get("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Sushi Roll"))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    @Test
    void cuandoObtenerUnoExiste_debeRetornarPedidoYStatus200() throws Exception {
        when(service.obtenerPorId(1)).thenReturn(Optional.of(pedidoResponseMock));

        mockMvc.perform(get("/api/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sushi Roll"));
    }

    @Test
    void cuandoObtenerUnoNoExiste_debeRetornarStatus404() throws Exception {
        when(service.obtenerPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pedidos/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoCambiarEstado_debeRetornarPedidoActualizadoYStatus200() throws Exception {
        // Creamos la respuesta esperada con el estado modificado
        PedidoResponseDTO pedidoActualizadoMock = new PedidoResponseDTO(
            1, 10, 20, "Sushi Roll", "SUSH-001-PROM", "Promoción 40 piezas", 2.0, 12990.0, 
            "EN_PREPARACION", 
            "2026-06-23"
        );

        when(service.actualizarEstado(1, "EN_PREPARACION")).thenReturn(Optional.of(pedidoActualizadoMock));

        // .param() simula perfectamente el @RequestParam de tu controlador
        mockMvc.perform(patch("/api/pedidos/1/estado")
                .param("nuevoEstado", "EN_PREPARACION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_PREPARACION"));
    }

@Test
    void cuandoEliminarExiste_debeRetornarStatus204() throws Exception {
        // Tu controlador evalúa un boolean directo, así que el service debe retornar true
        when(service.eliminar(1)).thenReturn(true);

        mockMvc.perform(delete("/api/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void cuandoEliminarNoExiste_debeRetornarStatus404() throws Exception {
        // Corregido: cambiamos 'false' por 'assertFalse' para usar la aserción correcta de JUnit
        when(service.eliminar(99)).thenReturn(false);

        mockMvc.perform(delete("/api/pedidos/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}