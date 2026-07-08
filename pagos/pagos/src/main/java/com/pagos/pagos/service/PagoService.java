// ✅ PagoService.java completo y correcto
package com.pagos.pagos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pagos.pagos.dto.PagoDetalleDTO;
import com.pagos.pagos.dto.PagoListadoDTO;
import com.pagos.pagos.model.Pago;
import com.pagos.pagos.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private RestTemplate restTemplate;



    //Listar todo como ListadoDTO
    public List<PagoListadoDTO> getPagosListado(){
        return repository.findAll().stream().map(p -> new PagoListadoDTO(
                p.getId(),
                p.getIdPedido(),
                p.getIdUsuario(),
                p.getMetodoPago(),
                p.getEstado()
        )).toList();
    }

    public Optional<PagoDetalleDTO> getPagoDetalle(Integer id) {
        return repository.findById(id).map(p -> new PagoDetalleDTO(
                p.getId(),
                p.getIdPedido(),
                p.getIdUsuario(),
                p.getMetodoPago(),
                p.getMonto(),
                p.getEstado()
        ));
    }

    public Pago addPago(Pago pago) {

        // Verifica que el usuario existe en ms-usuarios (puerto 8080)
        try {
            restTemplate.getForObject(
                "http://USUARIO/api/v1/usuarios/" + pago.getIdUsuario(),
                Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("El usuario con id " + pago.getIdUsuario() + " no existe");
        }

        // Verifica que el pedido existe en ms-pedidos (puerto 8083)
        try {
            restTemplate.getForObject(
                "http://PEDIDOS/api/pedidos/" + pago.getIdPedido(),
                Object.class
            );
        } catch (Exception e) {
            throw new RuntimeException("El pedido con id " + pago.getIdPedido() + " no existe");
        }

        return repository.save(pago);
    }

    public Optional<Pago> updatePago(Integer id, Pago nuevo) {
        return repository.findById(id).map(p -> {
            p.setIdPedido(nuevo.getIdPedido());
            p.setIdUsuario(nuevo.getIdUsuario());
            p.setMetodoPago(nuevo.getMetodoPago());
            p.setMonto(nuevo.getMonto());
            p.setEstado(nuevo.getEstado());
            return repository.save(p);
        });
    }

    public boolean deletePago(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}