package com.andycg.citas.config;

import com.andycg.citas.model.Rol;
import com.andycg.citas.model.Usuario;
import com.andycg.citas.repository.RolRepository;
import com.andycg.citas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

        @Bean
        CommandLineRunner initData(
                        RolRepository rolRepository,
                        UsuarioRepository usuarioRepository,
                        PasswordEncoder passwordEncoder) {
                return args -> {

                        Rol adminRol = rolRepository.findByNombre("ROLE_ADMIN")
                                        .orElseGet(() -> rolRepository.save(
                                                        new Rol(null, "ROLE_ADMIN")));

                        Rol userRol = rolRepository.findByNombre("ROLE_USUARIO")
                                        .orElseGet(() -> rolRepository.save(
                                                        new Rol(null, "ROLE_USUARIO")));
                        Rol mediRol = rolRepository.findByNombre("ROLE_MEDICO")
                                .orElseGet(() -> rolRepository.save(
                                                new Rol(null, "ROLE_MEDICO")));

                        if (usuarioRepository.findByUsername("admin@123").isEmpty()) {

                                Usuario admin = new Usuario();
                                admin.setUsername("admin@123");
                                admin.setPassword(
                                                passwordEncoder.encode("admin123"));
                                admin.setRol(adminRol);
                                admin.setEstado(true);

                                usuarioRepository.save(admin);
                        }
                };
        }
}