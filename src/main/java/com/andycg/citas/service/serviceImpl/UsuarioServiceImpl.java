package com.andycg.citas.service.serviceImpl;

import com.andycg.citas.config.JwtTokenProvider;
import com.andycg.citas.dto.*;
import com.andycg.citas.model.Rol;
import com.andycg.citas.model.Usuario;
import com.andycg.citas.repository.RolRepository;
import com.andycg.citas.repository.UsuarioRepository;
import com.andycg.citas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UsuarioResponseDTO registrar(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
        }

        Rol rol = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Rol por defecto no configurado"));

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername()); // Ahora usamos el username del DTO
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuario.setEstado(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        return mapToDTO(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO crear(RegisterRequest request) {

        if (request.getRolId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe seleccionar un rol");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
        }

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername()); // Ahora usamos el username del DTO
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuario.setEstado(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        return mapToDTO(usuarioRepository.save(usuario));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo no registrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = jwtTokenProvider.generateToken(usuario.getEmail(), usuario.getRol().getNombre());
        return new AuthResponse(token, usuario.getEmail(), usuario.getRol().getNombre());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAllWithRol().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioResponseDTO usuarioDTO) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        existente.setUsername(usuarioDTO.getUsername());
        existente.setEmail(usuarioDTO.getEmail()); // Ahora usamos el email del DTO si viene
        existente.setEstado(usuarioDTO.getEstado());

        if (usuarioDTO.getRolNombre() != null) {
            Rol rol = rolRepository.findByNombre(usuarioDTO.getRolNombre())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
            existente.setRol(rol);
        }

        return mapToDTO(usuarioRepository.save(existente));
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id, Boolean estado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuario.setEstado(estado);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .rolNombre(usuario.getRol().getNombre())
                .estado(usuario.getEstado())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
}
