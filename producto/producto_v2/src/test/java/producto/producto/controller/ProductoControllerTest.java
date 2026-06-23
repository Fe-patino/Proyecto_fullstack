package producto.producto.controller;

import producto.producto.Controller.ProductoController;
import producto.producto.dto.ProductoDetalleDTO;
import producto.producto.dto.ProductoListadoDTO;
import producto.producto.service.ProductoService;
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

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService service;

    @Test
    void listarProductos() throws Exception {

        when(service.listarDTO()).thenReturn(List.of(
                new ProductoListadoDTO(1, "Hamburguesa Clasica", 5990.0, true)
        ));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProductoPorId() throws Exception {

        when(service.buscarDetalleDTO(1)).thenReturn(Optional.of(
                new ProductoDetalleDTO(1, 1, 1, "Hamburguesa Clasica", "Carne, lechuga y tomate", 5990.0, true)
        ));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProductoPorIdNoExiste() throws Exception {

        when(service.buscarDetalleDTO(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }
}