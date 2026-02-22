package com.andycg.citas.service;

import com.andycg.citas.dto.MedicoRequestDTO;
import com.andycg.citas.dto.MedicoResponseDTO;

import java.util.List;

public interface MedicoService {

    MedicoResponseDTO registrar(MedicoRequestDTO medicoDTO);

    List<MedicoResponseDTO> listar();

    MedicoResponseDTO obtenerPorId(Long id);

    MedicoResponseDTO actualizar(Long id, MedicoRequestDTO medicoDTO);

    void eliminar(Long id);
}