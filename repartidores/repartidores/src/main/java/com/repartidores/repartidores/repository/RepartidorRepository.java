package com.repartidores.repartidores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.repartidores.repartidores.model.Repartidor;
import java.util.List;


@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Integer> {



    //Buscar po email
    Optional<Repartidor> findByEmail(String email);


    //Buscar por disponibilidad
    List<Repartidor> findByDisponible(Boolean disponible);

    //Buscar por patente 
    Optional<Repartidor> findByPatente(String patente);

}
