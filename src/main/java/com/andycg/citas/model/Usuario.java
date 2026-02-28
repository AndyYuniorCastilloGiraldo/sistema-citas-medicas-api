package com.andycg.citas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.io.Serializable;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
}