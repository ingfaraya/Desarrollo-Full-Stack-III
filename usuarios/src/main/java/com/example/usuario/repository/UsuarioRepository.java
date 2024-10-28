package com.example.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
