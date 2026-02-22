package com.andycg.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadResponseDTO {
    private Long idEspecialidad;
    private String nombre;
    private String descripcion;
}
