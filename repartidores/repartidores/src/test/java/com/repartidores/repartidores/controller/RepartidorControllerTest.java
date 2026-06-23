package com.repartidores.repartidores.controller;

import com.repartidores.repartidores.dto.RepartidorListadoDTO;
import com.repartidores.repartidores.service.RepartidorService;

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

@WebMvcTest(RepartidorController.class)
public class RepartidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepartidorService service;

    @Test
    void listarRepartidores() throws Exception {

        List<RepartidorListadoDTO> repartidores = List.of(
                new RepartidorListadoDTO(1, "Carlos Pérez Soto", "+56934567890", "Moto", true),
                new RepartidorListadoDTO(2, "Juan López Díaz", "+56923456789", "Bicicleta", false)
        );

        when(service.getRepartidoresListado()).thenReturn(repartidores);

        mockMvc.perform(get("/api/v1/repartidores"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarRepartidorPorId() throws Exception {

        var detalle = new com.repartidores.repartidores.dto.RepartidorDetalleDTO(
                1, 15678234, "5", "Carlos Pérez Soto",
                "carlos@gmail.com", "+56934567890",
                "BCDF12", "Moto", true
        );

        when(service.getRepartidorDetalle(1)).thenReturn(Optional.of(detalle));

        mockMvc.perform(get("/api/v1/repartidores/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarRepartidorNoExistente() throws Exception {

        when(service.getRepartidorDetalle(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/repartidores/999"))
                .andExpect(status().isNotFound());
    }
}
