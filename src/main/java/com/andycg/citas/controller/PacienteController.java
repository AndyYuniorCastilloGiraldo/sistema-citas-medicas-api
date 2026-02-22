package com.andycg.citas.controller;

import com.andycg.citas.dto.PacienteRequestDTO;
import com.andycg.citas.dto.PacienteResponseDTO;
import com.andycg.citas.service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private  PacienteService pacienteService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/listar")
    public ResponseEntity<List<PacienteResponseDTO>> listar() {
        return ResponseEntity.ok(pacienteService.listar());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<PacienteResponseDTO> registrar(@RequestBody PacienteRequestDTO pacienteDTO) {
        return new ResponseEntity<>(pacienteService.registrar(pacienteDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody PacienteRequestDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.actualizar(id, pacienteDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
