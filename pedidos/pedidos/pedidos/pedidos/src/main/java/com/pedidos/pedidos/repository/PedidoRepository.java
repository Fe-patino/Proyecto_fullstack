package com.pedidos.pedidos.repository;

import com.pedidos.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    // Buscar todos los pedidos de un cliente específico
    List<Pedido> findByUsuarioId(Integer usuarioId);
    
    // Buscar todos los pedidos que pertenecen a un restaurante
    List<Pedido> findByRestauranteId(Integer restauranteId);
    
    // Buscar pedidos por su estado (ej. todos los "PENDIENTES")
    List<Pedido> findByEstado(String estado);
}