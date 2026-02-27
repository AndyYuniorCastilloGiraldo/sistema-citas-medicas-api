package com.andycg.citas.controller;

import com.andycg.citas.dto.MedicoRequestDTO;
import com.andycg.citas.dto.MedicoResponseDTO;
import com.andycg.citas.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private  MedicoService medicoService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/listar")
    public ResponseEntity<List<MedicoResponseDTO>> listar() {
        return ResponseEntity.ok(medicoService.listar());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') or hasRole('MEDICO')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<MedicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO') ")
    @PostMapping("/registrar")
    public ResponseEntity<MedicoResponseDTO> registrar(@RequestBody MedicoRequestDTO medicoDTO) {
        return new ResponseEntity<>(medicoService.registrar(medicoDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MedicoResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody MedicoRequestDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.actualizar(id, medicoDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
