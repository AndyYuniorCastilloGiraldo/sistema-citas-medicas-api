package com.andycg.citas.controller;

import com.andycg.citas.dto.RegisterRequest;
import com.andycg.citas.dto.UsuarioResponseDTO;
import com.andycg.citas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
        @GetMapping("/listar")
        public ResponseEntity<List<UsuarioResponseDTO>> listar() {
                return ResponseEntity.ok(usuarioService.listar());
        }

        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/obtener/{id}")
        public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
                return ResponseEntity.ok(usuarioService.obtenerPorId(id));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/registrar")
        public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody RegisterRequest request) {
                return new ResponseEntity<>(usuarioService.registrar(request), HttpStatus.CREATED);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/actualizar/{id}")
        public ResponseEntity<UsuarioResponseDTO> actualizar(
                        @PathVariable Long id,
                        @RequestBody UsuarioResponseDTO usuarioDTO) {
                return ResponseEntity.ok(usuarioService.actualizar(id, usuarioDTO));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<Void> eliminar(@PathVariable Long id) {
                usuarioService.eliminar(id);
                return ResponseEntity.noContent().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PatchMapping("/{id}/estado")
        public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
                usuarioService.cambiarEstado(id, estado);
                return ResponseEntity.ok().build();
        }
}
