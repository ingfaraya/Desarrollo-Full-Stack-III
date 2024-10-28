package com.example.usuario.controller;

import com.example.usuario.model.Usuario;
import com.example.usuario.service.UsuarioService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public String login(@RequestBody Usuario usuario) {
        // Extraer las credenciales proporcionadas por el usuario
        String username = usuario.getUsername();
        String password = usuario.getPassword();

        // Validar las credenciales contra la base de datos
        Optional<Usuario> usuarioExistente = usuarioService.getUsuarioByUsername(username);
        
        if (usuarioExistente == null) {
            return "Usuario no encontrado";
        }

        if (!usuarioExistente.get().getPassword().equals(password)) {
            return "Credenciales incorrectas";
        }

        return "Login exitoso para el usuario: " + username;
    }
}
