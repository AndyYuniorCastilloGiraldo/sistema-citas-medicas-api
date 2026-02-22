package com.andycg.citas.repository;

import com.andycg.citas.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByFecha(LocalDate fecha);

    List<Cita> findByMedico_IdMedico(Long idMedico);

    List<Cita> findByPaciente_IdPaciente(Long idPaciente);
}