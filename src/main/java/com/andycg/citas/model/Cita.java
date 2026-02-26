package com.andycg.citas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

import java.io.Serializable;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;

    private String observaciones;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    // OPCIONAL: quién creó la cita (admin o paciente)
    @ManyToOne
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;
}