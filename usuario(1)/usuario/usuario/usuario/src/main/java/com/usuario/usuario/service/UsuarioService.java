package com.usuario.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuario.dto.UsuarioDetalleDTO;
import com.usuario.usuario.dto.UsuarioListadoDTO;
import com.usuario.usuario.model.Usuario;
import com.usuario.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> getUsuario(){
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id){
        return repository.findById(id);
    }

    public Usuario addUsuario (Usuario usuario){
        return repository.save(usuario); // SE SUPONE QUE save() guarda Y retorna el objeto con su ID generado
    }

    //ACTUALIZAR
    public Optional<Usuario> updateUsuario(Integer id, Usuario nuevo) {
        return repository.findById(id).map(user -> {   // Si existe, lo modifica
            user.setNombre(nuevo.getNombre());
            user.setAp_paterno(nuevo.getAp_paterno());
            user.setAp_materno(nuevo.getAp_materno());
            user.setRun(nuevo.getRun());
            user.setDv(nuevo.getDv());
            user.setEmail(nuevo.getEmail());
            user.setTelefono(nuevo.getTelefono());
            user.setCalle(nuevo.getCalle());
            user.setComuna(nuevo.getComuna());
            user.setRegion(nuevo.getRegion());
            user.setPais(nuevo.getPais());
            return repository.save(user);              
        });
    }


    //ELIMINAR
    public boolean deleteUsuario(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


    // Nuevo: listar como ListadoDTO
    public List<UsuarioListadoDTO> getUsuariosListado(){
        return repository.findAll().stream().map(user -> new UsuarioListadoDTO(
                                            user.getId(), 
                                            user.getNombre()+ " " + user.getAp_paterno() + " " + user.getAp_materno(),
                                            user.getEmail(),
                                            user.getTelefono()
                                            )).toList();
    }

    // Nuevo: buscar como DetalleDTO
    public Optional<UsuarioDetalleDTO> getUsuarioDetalle(Integer id){
        return repository.findById(id).map(user -> new UsuarioDetalleDTO(
                                            user.getId(),
                                            user.getRun(),
                                            user.getDv(),
                                            user.getNombre()+ " " + user.getAp_paterno() + " " + user.getAp_materno(),
                                            user.getEmail(),
                                            user.getTelefono(),
                                            user.getCalle(),
                                            user.getComuna(),
                                            user.getRegion(),
                                            user.getPais()
                                            ));
    }



}
