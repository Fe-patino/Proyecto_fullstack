package com.pagos.pagos.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagos.pagos.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    //Para buscar todos los pagos de un pedido
    List<Pago> findByIdPedido(Integer idPedido);

    //Para buscar todos los pagos de un usuario
    List<Pago> findByIdUsuario(Integer idUsuario);

    //Buscar por estado
    List<Pago> findByEstado(String estado);

}
