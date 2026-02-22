package com.andycg.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaRequestDTO {
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String observaciones;
    private Long idPaciente;
    private Long idMedico;
    private Long idUsuario;
}
