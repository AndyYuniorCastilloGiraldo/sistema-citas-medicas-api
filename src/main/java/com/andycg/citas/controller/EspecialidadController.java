package com.andycg.citas.controller;

import com.andycg.citas.dto.EspecialidadRequestDTO;
import com.andycg.citas.dto.EspecialidadResponseDTO;
import com.andycg.citas.service.EspecialidadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")

public class EspecialidadController {

    @Autowired
    private   EspecialidadService especialidadService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/listar")
    public ResponseEntity<List<EspecialidadResponseDTO>> listar() {
        return ResponseEntity.ok(especialidadService.listar());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<EspecialidadResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadService.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<EspecialidadResponseDTO> registrar(@RequestBody EspecialidadRequestDTO especialidadDTO) {
        return new ResponseEntity<>(especialidadService.registrar(especialidadDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EspecialidadResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody EspecialidadRequestDTO especialidadDTO) {
        return ResponseEntity.ok(especialidadService.actualizar(id, especialidadDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especialidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
