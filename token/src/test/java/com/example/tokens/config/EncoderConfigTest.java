package com.example.tokens.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class EncoderConfigTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        EncoderConfig encoderConfig = new EncoderConfig();
        this.passwordEncoder = encoderConfig.passwordEncoder();
    }

    @Test
    void testPasswordEncoder_NotNull() {
        // Verifica que el bean de PasswordEncoder no sea nulo
        assertNotNull(passwordEncoder, "El PasswordEncoder no debe ser nulo");
    }

    @Test
    void testPasswordEncoder_EncryptsPassword() {
        // Cifra una contraseña de ejemplo
        String rawPassword = "mypassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Verifica que la contraseña cifrada no sea igual a la original
        assertNotEquals(rawPassword, encodedPassword, "La contraseña cifrada no debe coincidir con la original");
    }

    @Test
    void testPasswordEncoder_EncodesDifferently() {
        // Cifra la misma contraseña dos veces
        String rawPassword = "mypassword";
        String encodedPassword1 = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);

        // Verifica que los resultados cifrados sean diferentes (porque BCrypt usa sal)
        assertNotEquals(encodedPassword1, encodedPassword2, "Cada cifrado debe generar un resultado diferente");
    }

    @Test
    void testPasswordEncoder_MatchesPassword() {
        // Cifra una contraseña de ejemplo
        String rawPassword = "mypassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Verifica que la contraseña en texto plano coincide con la cifrada
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), 
                "La contraseña en texto plano debe coincidir con la cifrada");
    }

    @Test
    void testPasswordEncoder_InvalidPasswordDoesNotMatch() {
        // Cifra una contraseña de ejemplo
        String rawPassword = "mypassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Verifica que una contraseña incorrecta no coincide con la cifrada
        assertFalse(passwordEncoder.matches("wrongpassword", encodedPassword),
                "Una contraseña incorrecta no debe coincidir con la cifrada");
    }
}
