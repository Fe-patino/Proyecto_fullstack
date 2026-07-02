package com.carrito.carrito.service;

import com.carrito.carrito.dto.CarritoItemRequestDTO;
import com.carrito.carrito.dto.CarritoItemResponseDTO;
import com.carrito.carrito.dto.CarritoResumenDTO;
import com.carrito.carrito.model.CarritoItem;
import com.carrito.carrito.repository.CarritoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private CarritoItemRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CarritoService carritoService;

    private CarritoItemRequestDTO requestDTO;
    private CarritoItem itemExistente;

    @BeforeEach
    void setUp() {
        // Inicializamos datos de prueba comunes
        requestDTO = new CarritoItemRequestDTO(
                1,          // usuarioId
                10,         // restauranteId
                "Hamburguesa", 
                "PROD1234567890", 
                "Con queso y papas", 
                2,          // cantidad
                1500.0      // precioUnitario
        );

        itemExistente = new CarritoItem();
        itemExistente.setId(100);
        itemExistente.setUsuarioId(1);
        itemExistente.setRestauranteId(10);
        itemExistente.setNombreProducto("Hamburguesa");
        itemExistente.setSkuProducto("PROD1234567890");
        itemExistente.setCantidad(2);
        itemExistente.setPrecioUnitario(1500.0);
        itemExistente.setEstado("ACTIVO");
        itemExistente.setFechaAgregado(LocalDateTime.now());
        itemExistente.setFechaActualizacion(LocalDateTime.now());
    }

    @Nested
    @DisplayName("Pruebas para agregarItem")
    class AgregarItemTests {

        @Test
        @DisplayName("Debe agregar un nuevo item exitosamente cuando el producto no existía en el carrito")
        void agregarNuevoItemExitosamente() {
            // Arrange
            // Simulamos que las validaciones de RestTemplate responden con éxito (cualquier objeto no-nulo)
            when(restTemplate.getForObject(eq("http://ms-usuarios/api/v1/usuarios/1"), eq(Object.class)))
                    .thenReturn(new Object());
            when(restTemplate.getForObject(eq("http://ms-restaurantes/api/restaurantes/10"), eq(Object.class)))
                    .thenReturn(new Object());

            // El producto NO existe en el carrito previamente
            when(repository.findByUsuarioIdAndSkuProductoAndEstado(1, "PROD1234567890", "ACTIVO"))
                    .thenReturn(Optional.empty());

            // Simulamos el guardado de JPA aportando el objeto simulado
            when(repository.save(any(CarritoItem.class))).thenReturn(itemExistente);

            // Act
            CarritoItemResponseDTO response = carritoService.agregarItem(requestDTO);

            // Assert
            assertNotNull(response);
            assertEquals(100, response.id());
            assertEquals("Hamburguesa", response.nombreProducto());
            assertEquals(3000.0, response.subtotal()); // 2 * 1500.0
            verify(repository, times(1)).save(any(CarritoItem.class));
        }

        @Test
        @DisplayName("Debe acumular la cantidad si el item ya existía de forma ACTIVA en el carrito")
        void acumularCantidadItemExistente() {
            // Arrange
            when(restTemplate.getForObject(anyString(), eq(Object.class))).thenReturn(new Object());
            
            // El producto SÍ existe en el carrito previamente
            when(repository.findByUsuarioIdAndSkuProductoAndEstado(1, "PROD1234567890", "ACTIVO"))
                    .thenReturn(Optional.of(itemExistente));
            
            when(repository.save(any(CarritoItem.class))).thenReturn(itemExistente);

            // Act
            carritoService.agregarItem(requestDTO);

            // Assert
            // El item existente tenía cantidad 2, y el DTO solicita 2 más -> Debe dar 4
            assertEquals(4, itemExistente.getCantidad());
            verify(repository, times(1)).save(itemExistente);
        }

        @Test
        @DisplayName("Debe lanzar una excepción si el servicio de usuarios falla o no encuentra al usuario")
        void lanzarExcepcionCuandoUsuarioNoExiste() {
            // Arrange
            // Forzamos al RestTemplate a lanzar un error al buscar al usuario
            when(restTemplate.getForObject(eq("http://ms-usuarios/api/v1/usuarios/1"), eq(Object.class)))
                    .thenThrow(new RuntimeException("Servicio no disponible"));

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                carritoService.agregarItem(requestDTO);
            });

            assertTrue(exception.getMessage().contains("El usuario con id 1 no existe"));
            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Pruebas para obtenerCarritoUsuario")
    class ObtenerCarritoTests {

        @Test
        @DisplayName("Debe retornar el resumen correcto del carrito con sus subtotales y totales calculados")
        void obtenerResumenCarritoExitosamente() {
            // Arrange
            Integer usuarioId = 1;
            List<CarritoItem> itemsActivos = Collections.singletonList(itemExistente);
            
            when(repository.findByUsuarioIdAndEstado(usuarioId, "ACTIVO")).thenReturn(itemsActivos);
            when(repository.calcularTotalCarrito(usuarioId)).thenReturn(3000.0);

            // Act
            CarritoResumenDTO resumen = carritoService.obtenerCarritoUsuario(usuarioId);

            // Assert
            assertNotNull(resumen);
            assertEquals(usuarioId, resumen.usuarioId());
            assertEquals(1, resumen.totalItems());
            assertEquals(3000.0, resumen.totalCarrito());
        }
    }

    @Nested
    @DisplayName("Pruebas para operaciones de actualización y borrado")
    class ModificacionCarritoTests {

        @Test
        @DisplayName("Debe cambiar el estado a ELIMINADO si la nueva cantidad es menor o igual a cero")
        void eliminarItemAlActualizarCantidadConCero() {
            // Arrange
            Integer itemId = 100;
            when(repository.findById(itemId)).thenReturn(Optional.of(itemExistente));
            when(repository.save(any(CarritoItem.class))).thenReturn(itemExistente);

            // Act
            Optional<CarritoItemResponseDTO> response = carritoService.actualizarCantidad(itemId, 0);

            // Assert
            assertTrue(response.isPresent());
            assertEquals("ELIMINADO", itemExistente.getEstado());
            verify(repository).save(itemExistente);
        }

        @Test
        @DisplayName("Debe cambiar el estado a CONFIRMADO de todos los items al procesar la confirmación")
        void confirmarCarritoExitosamente() {
            // Arrange
            Integer usuarioId = 1;
            List<CarritoItem> itemsActivos = Collections.singletonList(itemExistente);
            when(repository.findByUsuarioIdAndEstado(usuarioId, "ACTIVO")).thenReturn(itemsActivos);

            // Act
            boolean resultado = carritoService.confirmarCarrito(usuarioId);

            // Assert
            assertTrue(resultado);
            assertEquals("CONFIRMADO", itemExistente.getEstado());
            verify(repository).saveAll(itemsActivos);
        }
    }
}