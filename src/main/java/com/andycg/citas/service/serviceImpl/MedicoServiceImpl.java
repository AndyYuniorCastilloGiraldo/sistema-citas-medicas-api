package com.andycg.citas.service.serviceImpl;

import com.andycg.citas.dto.MedicoRequestDTO;
import com.andycg.citas.dto.MedicoResponseDTO;
import com.andycg.citas.model.Especialidad;
import com.andycg.citas.model.Medico;
import com.andycg.citas.repository.EspecialidadRepository;
import com.andycg.citas.repository.MedicoRepository;
import com.andycg.citas.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional
    public MedicoResponseDTO registrar(MedicoRequestDTO medicoDTO) {
        if (medicoRepository.findByCmp(medicoDTO.getCmp()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El CMP ya se encuentra registrado");
        }

        Especialidad especialidad = especialidadRepository.findById(medicoDTO.getIdEspecialidad())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada"));

        Medico medico = new Medico();
        mapToEntity(medicoDTO, medico);
        medico.setEspecialidad(especialidad);

        return mapToDTO(medicoRepository.save(medico));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> listar() {
        return medicoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedicoResponseDTO obtenerPorId(Long id) {
        return medicoRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));
    }

    @Override
    @Transactional
    public MedicoResponseDTO actualizar(Long id, MedicoRequestDTO medicoDTO) {
        Medico existente = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        medicoRepository.findByCmp(medicoDTO.getCmp())
                .ifPresent(m -> {
                    if (!m.getIdMedico().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El CMP ya pertenece a otro médico");
                    }
                });

        Especialidad especialidad = especialidadRepository.findById(medicoDTO.getIdEspecialidad())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada"));

        mapToEntity(medicoDTO, existente);
        existente.setEspecialidad(especialidad);

        return mapToDTO(medicoRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado");
        }
        medicoRepository.deleteById(id);
    }

    private void mapToEntity(MedicoRequestDTO dto, Medico entity) {
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setCmp(dto.getCmp());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
    }

    private MedicoResponseDTO mapToDTO(Medico entity) {
        MedicoResponseDTO dto = new MedicoResponseDTO();
        dto.setIdMedico(entity.getIdMedico());
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setCmp(entity.getCmp());
        dto.setTelefono(entity.getTelefono());
        dto.setCorreo(entity.getCorreo());
        
        if (entity.getEspecialidad() != null) {
            dto.setIdEspecialidad(entity.getEspecialidad().getIdEspecialidad());
            dto.setNombreEspecialidad(entity.getEspecialidad().getNombre());
        }
        
        return dto;
    }
}