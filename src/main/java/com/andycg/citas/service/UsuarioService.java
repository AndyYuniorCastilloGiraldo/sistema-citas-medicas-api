package com.andycg.citas.service;

import com.andycg.citas.dto.*;
import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO registrar(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    List<UsuarioResponseDTO> listar();

    UsuarioResponseDTO obtenerPorId(Long id);

    UsuarioResponseDTO actualizar(Long id, UsuarioResponseDTO usuarioDTO);

    void cambiarEstado(Long id, Boolean estado);

    void eliminar(Long id);
    UsuarioResponseDTO crear(RegisterRequest request);
    
}