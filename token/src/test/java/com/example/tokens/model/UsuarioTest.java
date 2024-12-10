package com.example.tokens.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("validuser");
        usuario.setPassword("validpassword");
        usuario.setRole("ADMIN");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.isEmpty(), "No debe haber violaciones para un usuario válido");
    }

    @Test
    void testEmptyUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsername("");
        usuario.setPassword("validpassword");
        usuario.setRole("ADMIN");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un nombre de usuario vacío",
                violations.iterator().next().getMessage());
    }

    @Test
    void testUsernameTooLong() {
        Usuario usuario = new Usuario();
        usuario.setUsername("a".repeat(51)); // 51 caracteres
        usuario.setPassword("validpassword");
        usuario.setRole("ADMIN");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("El nombre de usuario no debe exceder los 50 caracteres",
                violations.iterator().next().getMessage());
    }

    @Test
    void testShortPassword() {
        Usuario usuario = new Usuario();
        usuario.setUsername("validuser");
        usuario.setPassword("short"); // Menos de 8 caracteres
        usuario.setRole("ADMIN");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("La contraseña debe tener al menos 8 caracteres",
                violations.iterator().next().getMessage());
    }

    @Test
    void testEmptyRole() {
        Usuario usuario = new Usuario();
        usuario.setUsername("validuser");
        usuario.setPassword("validpassword");
        usuario.setRole("");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un rol vacío",
                violations.iterator().next().getMessage());
    }

    @Test
    void testRoleTooLong() {
        Usuario usuario = new Usuario();
        usuario.setUsername("validuser");
        usuario.setPassword("validpassword");
        usuario.setRole("A".repeat(21)); // 21 caracteres

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertEquals(1, violations.size());
        assertEquals("El rol no debe exceder los 20 caracteres",
                violations.iterator().next().getMessage());
    }
}
