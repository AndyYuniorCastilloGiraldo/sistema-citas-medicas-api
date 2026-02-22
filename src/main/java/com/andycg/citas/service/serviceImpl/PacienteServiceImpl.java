package com.andycg.citas.service.serviceImpl;

import com.andycg.citas.dto.PacienteRequestDTO;
import com.andycg.citas.dto.PacienteResponseDTO;
import com.andycg.citas.model.Paciente;
import com.andycg.citas.repository.PacienteRepository;
import com.andycg.citas.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    public PacienteResponseDTO registrar(PacienteRequestDTO pacienteDTO) {
        if (pacienteRepository.findByDni(pacienteDTO.getDni()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya se encuentra registrado");
        }

        Paciente paciente = new Paciente();
        mapToEntity(pacienteDTO, paciente);
        paciente.setFechaRegistro(LocalDateTime.now());

        Paciente guardado = pacienteRepository.save(paciente);
        return mapToDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listar() {
        return pacienteRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPorId(Long id) {
        return pacienteRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));
    }

    @Override
    @Transactional
    public PacienteResponseDTO actualizar(Long id, PacienteRequestDTO pacienteDTO) {
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));

        // Validar si el DNI pertenece a otro paciente
        pacienteRepository.findByDni(pacienteDTO.getDni())
                .ifPresent(p -> {
                    if (!p.getIdPaciente().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya pertenece a otro paciente");
                    }
                });

        mapToEntity(pacienteDTO, existente);
        return mapToDTO(pacienteRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    private void mapToEntity(PacienteRequestDTO dto, Paciente entity) {
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        entity.setDireccion(dto.getDireccion());
    }

    private PacienteResponseDTO mapToDTO(Paciente entity) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setIdPaciente(entity.getIdPaciente());
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setDni(entity.getDni());
        dto.setTelefono(entity.getTelefono());
        dto.setCorreo(entity.getCorreo());
        dto.setDireccion(entity.getDireccion());
        dto.setFechaRegistro(entity.getFechaRegistro());
        return dto;
    }
}