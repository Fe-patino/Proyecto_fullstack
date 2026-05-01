package com.usuario.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usuario.usuario.dto.UsuarioDetalleDTO;
import com.usuario.usuario.dto.UsuarioListadoDTO;
import com.usuario.usuario.model.Usuario;
import com.usuario.usuario.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // GET - Listar todos
    @GetMapping
    public ResponseEntity<List<UsuarioListadoDTO>> listarUsuarios() {
        return ResponseEntity.ok(service.getUsuariosListado());
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalleDTO> buscarUsuario(@PathVariable Integer id) {
        try {
            return service.getUsuarioDetalle(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
  

    // POST - Crear usuario
    @PostMapping
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody @Valid Usuario usuario) {
        try {
            Usuario nuevo = service.addUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);   // 201 Created
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT - Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody @Valid Usuario usuario) {
        try {
            return service.updateUsuario(id, usuario)
                    .map(ResponseEntity::ok)                        // Si existe → 200 OK
                    .orElse(ResponseEntity.notFound().build());     // Si no → 404
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE - Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            if (service.deleteUsuario(id)) {
                return ResponseEntity.noContent().build();          // 204 No Content
            } else {
                return ResponseEntity.notFound().build();           // 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
