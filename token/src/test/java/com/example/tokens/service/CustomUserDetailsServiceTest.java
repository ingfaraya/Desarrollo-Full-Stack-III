package com.example.tokens.service;

import com.example.tokens.model.Usuario;
import com.example.tokens.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsService();
        customUserDetailsService.usuarioRepository = usuarioRepository;
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Configurar un usuario simulado
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("testpassword");
        usuario.setRole("USER");

        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        // Ejecutar el método
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        // Verificar resultados
        assertNotNull(userDetails, "UserDetails no debe ser nulo");
        assertEquals("testuser", userDetails.getUsername(), "El nombre de usuario debe coincidir");
        assertEquals("testpassword", userDetails.getPassword(), "La contraseña debe coincidir");
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "El rol del usuario debe ser ROLE_USER");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        // Simula que el usuario no existe
        when(usuarioRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Ejecutar y verificar que se lanza la excepción
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonexistentuser");
        });

        assertEquals("Usuario no encontrado", exception.getMessage(), "El mensaje de la excepción debe ser correcto");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).findByUsername("nonexistentuser");
    }
}
