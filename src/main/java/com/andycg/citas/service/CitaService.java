package com.andycg.citas.service;

import com.andycg.citas.dto.CitaRequestDTO;
import com.andycg.citas.dto.CitaResponseDTO;

import java.util.List;

public interface CitaService {

    CitaResponseDTO registrar(CitaRequestDTO citaDTO);

    List<CitaResponseDTO> listar();

    CitaResponseDTO obtenerPorId(Long id);

    CitaResponseDTO actualizar(Long id, CitaRequestDTO citaDTO);

    void cancelar(Long id);
}