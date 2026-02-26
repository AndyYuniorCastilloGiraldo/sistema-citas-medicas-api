package com.andycg.citas.config;

import com.andycg.citas.model.Usuario;
import com.andycg.citas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final UsuarioRepository usuarioRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                Usuario usuario = usuarioRepository.findByUsernameWithRol(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                String roleName = usuario.getRol().getNombre();
                if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                }

                return new User(
                                usuario.getUsername(),
                                usuario.getPassword(),
                                usuario.getEstado(),
                                true,
                                true,
                                true,
                                Collections.singletonList(
                                                new SimpleGrantedAuthority(roleName)));
        }
}