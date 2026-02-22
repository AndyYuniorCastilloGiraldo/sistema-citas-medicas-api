package com.andycg.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {
    private Long idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;
    private String observaciones;
    private LocalDateTime fechaRegistro;

    // Simplificamos la respuesta para el frontend
    private Long idPaciente;
    private String nombrePaciente;
    private Long idMedico;
    private String nombreMedico;
    private Long idUsuario;
    private String nombreUsuario;
}
