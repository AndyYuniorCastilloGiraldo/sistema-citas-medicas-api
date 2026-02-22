package com.andycg.citas.service.serviceImpl;

import com.andycg.citas.dto.EspecialidadRequestDTO;
import com.andycg.citas.dto.EspecialidadResponseDTO;
import com.andycg.citas.model.Especialidad;
import com.andycg.citas.repository.EspecialidadRepository;
import com.andycg.citas.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional
    public EspecialidadResponseDTO registrar(EspecialidadRequestDTO especialidadDTO) {
        Especialidad especialidad = new Especialidad();
        mapToEntity(especialidadDTO, especialidad);
        return mapToDTO(especialidadRepository.save(especialidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadResponseDTO> listar() {
        return especialidadRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EspecialidadResponseDTO obtenerPorId(Long id) {
        return especialidadRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada"));
    }

    @Override
    @Transactional
    public EspecialidadResponseDTO actualizar(Long id, EspecialidadRequestDTO especialidadDTO) {
        Especialidad existente = especialidadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada"));

        mapToEntity(especialidadDTO, existente);
        return mapToDTO(especialidadRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!especialidadRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada");
        }
        especialidadRepository.deleteById(id);
    }

    private void mapToEntity(EspecialidadRequestDTO dto, Especialidad entity) {
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
    }

    private EspecialidadResponseDTO mapToDTO(Especialidad entity) {
        EspecialidadResponseDTO dto = new EspecialidadResponseDTO();
        dto.setIdEspecialidad(entity.getIdEspecialidad());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }
}