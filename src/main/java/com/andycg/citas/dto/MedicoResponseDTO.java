package com.andycg.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoResponseDTO {
    private Long idMedico;
    private String nombres;
    private String apellidos;
    private String cmp;
    private String telefono;
    private String correo;
    private String nombreEspecialidad;
    private Long idEspecialidad;
}
