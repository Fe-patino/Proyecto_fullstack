package com.repartidores.repartidores.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.repartidores.repartidores.dto.RepartidorDetalleDTO;
import com.repartidores.repartidores.dto.RepartidorListadoDTO;
import com.repartidores.repartidores.model.Repartidor;
import com.repartidores.repartidores.repository.RepartidorRepository;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Repartidor addRepartidor(Repartidor repartidor){
        if(repartidor.getIdPedido() != null){
            try{
                restTemplate.getForObject(
                    "http://localhost:8083/api/pedidos/" + 
                    repartidor.getIdPedido(),
                    Object.class
                    );
                
            }catch(Exception e){
                
                throw new RuntimeException("El pedido con id " + repartidor.getIdPedido() + " no existe");  
                
            }
        }
        return repository.save(repartidor);
        
    }

// Listar todos como ListadoDTO
    public List<RepartidorListadoDTO> getRepartidoresListado() {
        return repository.findAll().stream().map(r -> new RepartidorListadoDTO(
                                    r.getId(),
                                    r.getNombre() + " " + r.getAp_paterno() + " " + r.getAp_materno(),
                                    r.getTelefono(),
                                    r.getTipoVehiculo(),
                                    r.getDisponible()
                                )).toList();
    }


// Buscar por ID como DetalleDTO
    public Optional<RepartidorDetalleDTO> getRepartidorDetalle(Integer id) {
        return repository.findById(id).map(r -> new RepartidorDetalleDTO(
                        r.getId(),
                        r.getRun(),
                        r.getDv(),
                        r.getNombre() + " " + r.getAp_paterno() + " " + r.getAp_materno(),
                        r.getEmail(),
                        r.getTelefono(),
                        r.getPatente(),
                        r.getTipoVehiculo(),
                        r.getDisponible()
                ));
    }






// Actualizar repartidor en caso de ser necesario. Por ID
    public Optional<Repartidor> updateRepartidor(Integer id, Repartidor nuevo) {
        return repository.findById(id).map(r -> {
            r.setNombre(nuevo.getNombre());
            r.setAp_paterno(nuevo.getAp_paterno());
            r.setAp_materno(nuevo.getAp_materno());
            r.setRun(nuevo.getRun());
            r.setDv(nuevo.getDv());
            r.setEmail(nuevo.getEmail());
            r.setTelefono(nuevo.getTelefono());
            r.setPatente(nuevo.getPatente());
            r.setTipoVehiculo(nuevo.getTipoVehiculo());
            r.setDisponible(nuevo.getDisponible());
            r.setIdPedido(nuevo.getIdPedido());
            return repository.save(r);
        });
    }


// Eliminar repartidor
    public boolean deleteRepartidor(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
