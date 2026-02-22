package com.andycg.citas.controller;

import com.andycg.citas.dto.CitaRequestDTO;
import com.andycg.citas.dto.CitaResponseDTO;
import com.andycg.citas.service.CitaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private  CitaService citaService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/listar")
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @PostMapping("/registrar")
    public ResponseEntity<CitaResponseDTO> registrar(@RequestBody CitaRequestDTO citaDTO) {
        return new ResponseEntity<>(citaService.registrar(citaDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(@PathVariable Long id, @RequestBody CitaRequestDTO citaDTO) {
        return ResponseEntity.ok(citaService.actualizar(id, citaDTO));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
