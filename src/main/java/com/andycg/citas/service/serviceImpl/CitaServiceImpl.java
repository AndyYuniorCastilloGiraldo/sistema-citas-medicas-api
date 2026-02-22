package com.andycg.citas.service.serviceImpl;

import com.andycg.citas.dto.CitaRequestDTO;
import com.andycg.citas.dto.CitaResponseDTO;
import com.andycg.citas.model.Cita;
import com.andycg.citas.model.Medico;
import com.andycg.citas.model.Paciente;
import com.andycg.citas.model.Usuario;
import com.andycg.citas.repository.CitaRepository;
import com.andycg.citas.repository.MedicoRepository;
import com.andycg.citas.repository.PacienteRepository;
import com.andycg.citas.repository.UsuarioRepository;
import com.andycg.citas.service.CitaService;
import com.andycg.citas.service.SerializationService;
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
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SerializationService serializationService;

    @Override
    @Transactional
    public CitaResponseDTO registrar(CitaRequestDTO citaDTO) {
        Paciente paciente = pacienteRepository.findById(citaDTO.getIdPaciente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));

        Medico medico = medicoRepository.findById(citaDTO.getIdMedico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        Usuario usuario = usuarioRepository.findById(citaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Cita cita = new Cita();
        mapToEntity(citaDTO, cita);
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setUsuario(usuario);
        cita.setEstado("PENDIENTE");
        cita.setFechaRegistro(LocalDateTime.now());

        Cita guardada = citaRepository.save(cita);

        // RESPALDO BINARIO PROFESIONAL
        serializationService.guardarCita(guardada);

        return mapToDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponseDTO> listar() {
        return citaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponseDTO obtenerPorId(Long id) {
        return citaRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
    }

    @Override
    @Transactional
    public CitaResponseDTO actualizar(Long id, CitaRequestDTO citaDTO) {
        Cita existente = citaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));

        Paciente paciente = pacienteRepository.findById(citaDTO.getIdPaciente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));

        Medico medico = medicoRepository.findById(citaDTO.getIdMedico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        mapToEntity(citaDTO, existente);
        existente.setPaciente(paciente);
        existente.setMedico(medico);

        return mapToDTO(citaRepository.save(existente));
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
        cita.setEstado("CANCELADA");
        citaRepository.save(cita);
    }

    private void mapToEntity(CitaRequestDTO dto, Cita entity) {
        entity.setFecha(dto.getFecha());
        entity.setHora(dto.getHora());
        entity.setMotivo(dto.getMotivo());
        entity.setObservaciones(dto.getObservaciones());
    }

    private CitaResponseDTO mapToDTO(Cita entity) {
        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setIdCita(entity.getIdCita());
        dto.setFecha(entity.getFecha());
        dto.setHora(entity.getHora());
        dto.setMotivo(entity.getMotivo());
        dto.setEstado(entity.getEstado());
        dto.setObservaciones(entity.getObservaciones());
        dto.setFechaRegistro(entity.getFechaRegistro());

        dto.setIdPaciente(entity.getPaciente().getIdPaciente());
        dto.setNombrePaciente(entity.getPaciente().getNombres() + " " + entity.getPaciente().getApellidos());

        dto.setIdMedico(entity.getMedico().getIdMedico());
        dto.setNombreMedico(entity.getMedico().getNombres() + " " + entity.getMedico().getApellidos());

        dto.setIdUsuario(entity.getUsuario().getIdUsuario());
        dto.setNombreUsuario(entity.getUsuario().getUsername());

        return dto;
    }
}