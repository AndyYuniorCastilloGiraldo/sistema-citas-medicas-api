package com.andycg.citas.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String username;
    private String email;
    private String rolNombre;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
}
