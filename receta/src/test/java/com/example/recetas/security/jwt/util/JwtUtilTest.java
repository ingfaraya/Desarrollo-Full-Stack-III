package com.example.recetas.security.jwt.util;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secretKeyBase64 = "bW9yZXNlY3JldGV4YW1wbGVzZWNyZXRrZXkxMjM0NTY3ODkw"; // Ejemplo válido de 256 bits
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.setSecretKey(secretKeyBase64);
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyBase64));
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token, "El token generado no debe ser nulo.");
        assertFalse(token.isEmpty(), "El token generado no debe estar vacío.");
    }

    @Test
    void testValidateToken_ValidToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token);
        assertTrue(isValid, "El token válido debería pasar la validación.");
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.INVALID_SIGNATURE";

        boolean isValid = jwtUtil.validateToken(invalidToken);
        assertFalse(isValid, "Un token inválido no debería pasar la validación.");
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.getUsernameFromToken(token);
        assertEquals(username, extractedUsername, "El nombre de usuario extraído debe coincidir con el original.");
    }

    @Test
    void testValidateToken_ExpiredToken() {
        // Simula un token con expiración manualmente reducida para este test
        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 86400000)) // Hace 24 horas
                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // Expirado hace 1 hora
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtUtil.validateToken(expiredToken);
        assertFalse(isValid, "Un token expirado no debería ser válido.");
    }
}
