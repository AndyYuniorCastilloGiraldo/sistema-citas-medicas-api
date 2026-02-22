package com.andycg.citas.service;

import com.andycg.citas.dto.EspecialidadRequestDTO;
import com.andycg.citas.dto.EspecialidadResponseDTO;

import java.util.List;

public interface EspecialidadService {

    EspecialidadResponseDTO registrar(EspecialidadRequestDTO especialidadDTO);

    List<EspecialidadResponseDTO> listar();

    EspecialidadResponseDTO obtenerPorId(Long id);

    EspecialidadResponseDTO actualizar(Long id, EspecialidadRequestDTO especialidadDTO);

    void eliminar(Long id);
}