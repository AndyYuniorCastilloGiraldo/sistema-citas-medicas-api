package com.andycg.citas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.io.Serializable;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;

    private String nombres;
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String dni;

    private String telefono;
    private String correo;
    private String direccion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}