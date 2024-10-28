package com.example.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);

    default Optional<Usuario> getUsuarioByUsername(String username) {
        return findByUsername(username);
    }
    
}
