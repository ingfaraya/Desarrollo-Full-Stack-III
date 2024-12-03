package com.example.tokens.repository;

import com.example.tokens.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar el usuario de ejemplo
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
        usuario.setPassword("testpassword");
        usuario.setRole("USER");
    }

    @Test
    void testFindByUsername_UserExists() {
        // Simula el comportamiento del repositorio
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        // Ejecuta el método
        Optional<Usuario> result = usuarioRepository.findByUsername("testuser");

        // Verifica los resultados
        assertTrue(result.isPresent(), "El usuario debería existir");
        assertEquals("testuser", result.get().getUsername());
        assertEquals("testpassword", result.get().getPassword());
        assertEquals("USER", result.get().getRole());

        // Verifica que el método fue llamado
        verify(usuarioRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testFindByUsername_UserDoesNotExist() {
        // Simula que el usuario no existe
        when(usuarioRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Ejecuta el método
        Optional<Usuario> result = usuarioRepository.findByUsername("nonexistentuser");

        // Verifica los resultados
        assertFalse(result.isPresent(), "El usuario no debería existir");

        // Verifica que el método fue llamado
        verify(usuarioRepository, times(1)).findByUsername("nonexistentuser");
    }

    @Test
    void testSaveUser() {
        // Simula el comportamiento del método save
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Ejecuta el método
        Usuario savedUser = usuarioRepository.save(usuario);

        // Verifica los resultados
        assertNotNull(savedUser, "El usuario guardado no debe ser nulo");
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("testpassword", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());

        // Verifica que el método fue llamado
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
