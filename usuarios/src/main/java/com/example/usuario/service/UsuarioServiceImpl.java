package com.example.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuario.model.Usuario;
import com.example.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    public UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    @Override
    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuario) {
        return usuarioRepository.findById(id)
            .map(existingUsuario -> {
                usuario.setId(id);
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuario not found with id: " + id));  // Consider using a custom exception
    }

    @Override
    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> getUsuarioByUsername(String username) {
        return usuarioRepository.getUsuarioByUsername(username);
    }
}
