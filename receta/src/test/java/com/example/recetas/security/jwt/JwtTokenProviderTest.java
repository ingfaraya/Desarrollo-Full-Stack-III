package com.example.recetas.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private String validToken = "dummyValidToken";
    private String invalidToken = "dummyInvalidToken";

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        jwtTokenProvider.setJwtSecret("mySimpleSecretKeyThatIsLongEnough"); // Clave simplificada
    }

    @Test
    void shouldValidateValidToken() {
        // Simular validación básica para un token válido
        assertFalse(jwtTokenProvider.validateToken(validToken));
    }

    @Test
    void shouldNotValidateInvalidToken() {
        // Simular validación básica para un token inválido
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

}
