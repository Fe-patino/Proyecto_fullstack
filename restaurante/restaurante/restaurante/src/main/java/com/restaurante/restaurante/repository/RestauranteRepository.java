package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    // Método para verificar duplicados
    boolean existsByNombre(String nombre);
}