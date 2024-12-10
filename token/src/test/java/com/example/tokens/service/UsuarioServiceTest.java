package com.example.tokens.service;

import com.example.tokens.model.Usuario;
import com.example.tokens.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder);
    }

    @Test
    void testGetAllUsuarios() {
        // Configurar usuarios simulados
        List<Usuario> usuarios = Arrays.asList(
                new Usuario() {{ setId(1L); setUsername("user1"); setPassword("pass1"); setRole("ROLE_USER"); }},
                new Usuario() {{ setId(2L); setUsername("user2"); setPassword("pass2"); setRole("ROLE_ADMIN"); }}
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Ejecutar el método
        List<Usuario> result = usuarioService.getAllUsuarios();

        // Verificar resultados
        assertEquals(2, result.size(), "Debe devolver la cantidad correcta de usuarios");
        assertEquals("user1", result.get(0).getUsername(), "El primer usuario debe ser 'user1'");
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testGetUsuarioById_UserExists() {
        // Configurar un usuario simulado
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
        usuario.setPassword("testpassword");
        usuario.setRole("ROLE_USER");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Ejecutar el método
        Usuario result = usuarioService.getUsuarioById(1L);

        // Verificar resultados
        assertNotNull(result, "El usuario no debe ser nulo");
        assertEquals("testuser", result.getUsername(), "El nombre de usuario debe coincidir");
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUsuarioById_UserNotFound() {
        // Configurar el repositorio para devolver vacío
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar y verificar la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.getUsuarioById(1L));
        assertEquals("Usuario no encontrado", exception.getMessage(), "El mensaje de la excepción debe ser correcto");
    }

    @Test
    void testCreateUsuario() {
        // Configurar usuario simulado
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("rawpassword");
        usuario.setRole("ROLE_USER");

        // Simular encriptación de contraseña
        when(passwordEncoder.encode("rawpassword")).thenReturn("encryptedpassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Ejecutar el método
        Usuario result = usuarioService.createUsuario(usuario);

        // Verificar resultados
        assertNotNull(result, "El usuario creado no debe ser nulo");
        assertEquals("encryptedpassword", result.getPassword(), "La contraseña debe estar encriptada");
        verify(passwordEncoder, times(1)).encode("rawpassword");
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testUpdateUsuario() {
        // Configurar usuario existente
        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1L);
        existingUsuario.setUsername("existinguser");
        existingUsuario.setPassword("oldpassword");
        existingUsuario.setRole("ROLE_USER");

        // Configurar nuevos detalles del usuario
        Usuario updatedDetails = new Usuario();
        updatedDetails.setUsername("updateduser");
        updatedDetails.setPassword("newpassword");
        updatedDetails.setRole("ROLE_ADMIN");

        // Simular interacciones
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existingUsuario));
        when(passwordEncoder.encode("newpassword")).thenReturn("encryptednewpassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        // Ejecutar el método
        Usuario result = usuarioService.updateUsuario(1L, updatedDetails);

        // Verificar resultados
        assertNotNull(result, "El usuario actualizado no debe ser nulo");
        assertEquals("updateduser", result.getUsername(), "El nombre de usuario debe actualizarse");
        assertEquals("encryptednewpassword", result.getPassword(), "La contraseña debe estar encriptada");
        assertEquals("ROLE_ADMIN", result.getRole(), "El rol debe actualizarse");
        verify(usuarioRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(usuarioRepository, times(1)).save(existingUsuario);
    }

    @Test
    void testDeleteUsuario() {
        // Configurar usuario existente
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Ejecutar el método
        usuarioService.deleteUsuario(1L);

        // Verificar interacciones
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Configurar un usuario simulado
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("testpassword");
        usuario.setRole("ROLE_USER");

        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        // Ejecutar el método
        Usuario result = usuarioService.loadUserByUsername("testuser");

        // Verificar resultados
        assertNotNull(result, "El usuario no debe ser nulo");
        assertEquals("testuser", result.getUsername(), "El nombre de usuario debe coincidir");
        verify(usuarioRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Configurar el repositorio para devolver vacío
        when(usuarioRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Ejecutar y verificar la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.loadUserByUsername("nonexistentuser"));
        assertEquals("Usuario no encontrado", exception.getMessage(), "El mensaje de la excepción debe ser correcto");
    }
}
