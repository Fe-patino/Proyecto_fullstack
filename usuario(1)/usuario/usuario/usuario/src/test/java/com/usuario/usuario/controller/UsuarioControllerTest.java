package com.usuario.usuario.controller;

import com.usuario.usuario.dto.UsuarioListadoDTO;
import com.usuario.usuario.service.UsuarioService;

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

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP

    @MockitoBean
    private UsuarioService service; // Service falso, sin BD

    @Test
    void listarUsuarios() throws Exception {

        // Datos de prueba ficticios
        List<UsuarioListadoDTO> usuarios = List.of(
                new UsuarioListadoDTO(1, "Carlos González Pérez", "carlos@clickandeat.cl", "+56912345678"),
                new UsuarioListadoDTO(2, "Lina García López", "lina@clickandeat.cl", "+56987654321")
        );

        // Le dice al Service falso qué devolver
        when(service.getUsuariosListado()).thenReturn(usuarios);

        // Simula GET /api/v1/usuarios y verifica que responde 200
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarUsuarioPorId() throws Exception {

        // Dato de prueba ficticio
        var detalle = new com.usuario.usuario.dto.UsuarioDetalleDTO(
                1, 12345678, "9", "Carlos González Pérez",
                "carlos@clickandeat.cl", "+56912345678",
                "Av. Providencia 123", "Providencia",
                "Región Metropolitana", "Chile"
        );

        // Le dice al Service falso qué devolver cuando busquen id=1
        when(service.getUsuarioDetalle(1)).thenReturn(Optional.of(detalle));

        // Simula GET /api/v1/usuarios/1 y verifica que responde 200
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarUsuarioNoExistente() throws Exception {

        // Le dice al Service falso que devuelva vacío para id=999
        when(service.getUsuarioDetalle(999)).thenReturn(Optional.empty());

        // Simula GET /api/v1/usuarios/999 y verifica que responde 404
        mockMvc.perform(get("/api/v1/usuarios/999"))
                .andExpect(status().isNotFound());
    }
}
