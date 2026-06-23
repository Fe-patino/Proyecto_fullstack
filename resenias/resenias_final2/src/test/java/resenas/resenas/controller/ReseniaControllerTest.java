package resenas.resenas.controller;

import resenas.resenas.dto.ReseniaDetalleDTO;
import resenas.resenas.service.ReseniaService;
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

@WebMvcTest(ReseniaController.class)
public class ReseniaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReseniaService service;

    @Test
    void listarResenias() throws Exception {

        ReseniaDetalleDTO resenia = new ReseniaDetalleDTO(
                1, 10, 1, 1, 5, 4, "Muy buena comida", "2024-01-01T10:00:00"
        );

        when(service.obtenerTodas()).thenReturn(List.of(resenia));

        mockMvc.perform(get("/api/resenias"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerReseniaPorId() throws Exception {

        ReseniaDetalleDTO resenia = new ReseniaDetalleDTO(
                1, 10, 1, 1, 5, 4, "Muy buena comida", "2024-01-01T10:00:00"
        );

        when(service.obtenerPorId(1)).thenReturn(Optional.of(resenia));

        mockMvc.perform(get("/api/resenias/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerReseniaPorIdNoExiste() throws Exception {

        when(service.obtenerPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/resenias/99"))
                .andExpect(status().isNotFound());
    }
}