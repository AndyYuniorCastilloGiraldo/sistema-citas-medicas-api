package com.andycg.citas.service;

import com.andycg.citas.dto.PacienteRequestDTO;
import com.andycg.citas.dto.PacienteResponseDTO;

import java.util.List;

public interface PacienteService {

    PacienteResponseDTO registrar(PacienteRequestDTO pacienteDTO);

    List<PacienteResponseDTO> listar();

    PacienteResponseDTO obtenerPorId(Long id);

    PacienteResponseDTO actualizar(Long id, PacienteRequestDTO pacienteDTO);

    void eliminar(Long id);
}