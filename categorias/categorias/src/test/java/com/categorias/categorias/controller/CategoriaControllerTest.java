package com.categorias.categorias.controller;

import com.categorias.categorias.dto.CategoriaResponseDTO;
import com.categorias.categorias.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void listarTodasLasCategorias() throws Exception {
        when(categoriaService.obtenerTodasLasCategorias()).thenReturn(List.of());
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
    }

    @Test
    void listarCategoriasActivas() throws Exception {
        when(categoriaService.obtenerCategoriasActivas()).thenReturn(List.of());
        mockMvc.perform(get("/api/categorias/activas"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarCategoriaPorId() throws Exception {
        CategoriaResponseDTO categoria = new CategoriaResponseDTO();
        categoria.setId(1L);
        categoria.setNombre("Comida Rápida");
        categoria.setDescripcion("Hamburguesas y similares");
        categoria.setActivo(true);
        when(categoriaService.obtenerCategoriaPorId(1L)).thenReturn(categoria);
        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk());
    }
}