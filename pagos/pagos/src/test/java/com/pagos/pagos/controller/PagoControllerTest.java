package com.pagos.pagos.controller;

import com.pagos.pagos.dto.PagoListadoDTO;
import com.pagos.pagos.service.PagoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService service;

    @Test
    void listarPagos() throws Exception {

        List<PagoListadoDTO> pagos = List.of(
                new PagoListadoDTO(1, 1, 1, "Tarjeta", "Completado"),
                new PagoListadoDTO(2, 2, 2, "Efectivo", "Pendiente")
        );

        when(service.getPagosListado()).thenReturn(pagos);

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPagoPorId() throws Exception {

        var detalle = new com.pagos.pagos.dto.PagoDetalleDTO(
                1, 1, 1, "Tarjeta", 15000, "Completado"
        );

        when(service.getPagoDetalle(1)).thenReturn(Optional.of(detalle));

        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPagoNoExistente() throws Exception {

        when(service.getPagoDetalle(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pagos/999"))
                .andExpect(status().isNotFound());
    }
}
