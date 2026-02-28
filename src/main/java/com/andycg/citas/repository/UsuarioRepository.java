package com.andycg.citas.repository;

import com.andycg.citas.model.Usuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol")
    List<Usuario> findAllWithRol();

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRol(@Param("username") String username);

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.email = :email")
    Optional<Usuario> findByEmailWithRol(@Param("email") String email);

    boolean existsByEmail(String email);
}