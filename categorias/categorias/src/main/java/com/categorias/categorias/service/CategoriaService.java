package com.categorias.categorias.service;

import com.categorias.categorias.dto.CategoriaDTO;
import com.categorias.categorias.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponseDTO> obtenerTodasLasCategorias();
    List<CategoriaResponseDTO> obtenerCategoriasActivas();
    CategoriaResponseDTO obtenerCategoriaPorId(Long id);
    CategoriaResponseDTO crearCategoria(CategoriaDTO categoriaDTO);
    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO);
    void eliminarCategoriaLogica(Long id);
}