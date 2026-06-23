package notificaciones.notificaciones.controller;

import notificaciones.notificaciones.dto.NotificacionDetalleDTO;
import notificaciones.notificaciones.service.NotificacionService;
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

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService service;

    @Test
    void listarNotificaciones() throws Exception {

        when(service.obtenerTodas()).thenReturn(List.of(
                new NotificacionDetalleDTO(1, 1, "CLIENTE", "PEDIDO_CONFIRMADO", "PUSH",
                        "Pedido confirmado", "Tu pedido fue confirmado", 1, null, false, "2024-01-01")
        ));

        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerNotificacionPorId() throws Exception {

        when(service.obtenerPorId(1)).thenReturn(Optional.of(
                new NotificacionDetalleDTO(1, 1, "CLIENTE", "PEDIDO_CONFIRMADO", "PUSH",
                        "Pedido confirmado", "Tu pedido fue confirmado", 1, null, false, "2024-01-01")
        ));

        mockMvc.perform(get("/api/notificaciones/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerNotificacionPorIdNoExiste() throws Exception {

        when(service.obtenerPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/notificaciones/99"))
                .andExpect(status().isNotFound());
    }
}
