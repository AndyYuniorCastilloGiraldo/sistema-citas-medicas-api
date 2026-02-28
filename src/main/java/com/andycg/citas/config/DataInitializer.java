package com.andycg.citas.config;

import com.andycg.citas.model.Rol;
import com.andycg.citas.model.Usuario;
import com.andycg.citas.repository.RolRepository;
import com.andycg.citas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

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

                        // Crear admin solo si no existe
                        String adminEmail = "admin@citas.com";
                        if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {

                                Usuario admin = new Usuario();
                                admin.setUsername(adminEmail);
                                admin.setEmail(adminEmail);
                                admin.setPassword(passwordEncoder.encode("admin123"));
                                admin.setRol(adminRol);
                                admin.setEstado(true);
                                admin.setFechaCreacion(LocalDateTime.now());

                                usuarioRepository.save(admin);
                                System.out.println("✅ Admin creado: " + adminEmail + " / admin123");
                        }
                };
        }
}
