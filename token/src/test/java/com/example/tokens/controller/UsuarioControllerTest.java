package com.example.tokens.controller;

import com.example.tokens.model.Usuario;
import com.example.tokens.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsuarios_shouldReturnListOfUsuarios() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(
                new Usuario() {{
                    setId(1L);
                    setUsername("user1");
                    setPassword("password123");
                    setRole("USER");
                }},
                new Usuario() {{
                    setId(2L);
                    setUsername("admin1");
                    setPassword("securepassword");
                    setRole("ADMIN");
                }}
        );
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        // Act
        ResponseEntity<List<Usuario>> response = usuarioController.getAllUsuarios();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    void getUsuarioById_shouldReturnUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("user1");
        usuario.setPassword("password123");
        usuario.setRole("USER");

        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.getUsuarioById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getBody().getId());
        assertEquals("user1", response.getBody().getUsername());
        verify(usuarioService, times(1)).getUsuarioById(1L);
    }

    @Test
    void createUsuario_shouldReturnCreatedUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("newUser");
        usuario.setPassword("newPassword123");
        usuario.setRole("USER");

        Usuario createdUsuario = new Usuario();
        createdUsuario.setId(3L);
        createdUsuario.setUsername("newUser");
        createdUsuario.setPassword("newPassword123");
        createdUsuario.setRole("USER");

        when(usuarioService.createUsuario(usuario)).thenReturn(createdUsuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.createUsuario(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(3L, response.getBody().getId());
        assertEquals("newUser", response.getBody().getUsername());
        verify(usuarioService, times(1)).createUsuario(usuario);
    }

    @Test
    void updateUsuario_shouldReturnUpdatedUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("updatedUser");
        usuario.setPassword("updatedPassword123");
        usuario.setRole("USER");

        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setId(1L);
        updatedUsuario.setUsername("updatedUser");
        updatedUsuario.setPassword("updatedPassword123");
        updatedUsuario.setRole("USER");

        when(usuarioService.updateUsuario(1L, usuario)).thenReturn(updatedUsuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.updateUsuario(1L, usuario);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getBody().getId());
        assertEquals("updatedUser", response.getBody().getUsername());
        verify(usuarioService, times(1)).updateUsuario(1L, usuario);
    }

    @Test
    void deleteUsuario_shouldReturnNoContent() {
        // Act
        ResponseEntity<Void> response = usuarioController.deleteUsuario(1L);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).deleteUsuario(1L);
    }
}
