package com.example.tokens.security.jwt.util;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    private String username = "testUser";
    private String secretKeyBase64 = "a2x5ZWEgdXNlIHNvbWV0aGluZyBsb25nIGFuZCBzZWN1cmU="; // Base64 encoded key

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.setSecretKey(secretKeyBase64);
    }

    @Test
    void testGenerateTokenAndValidateToken() {
        // Generate token
        String token = jwtUtil.generateToken(username);

        // Validate token
        boolean isValid = jwtUtil.validateToken(token);

        // Assertions
        assertNotNull(token);
        assertTrue(isValid);
    }

    @Test
    void testGetUsernameFromToken() {
        // Generate token
        String token = jwtUtil.generateToken(username);

        // Extract username
        String extractedUsername = jwtUtil.getUsernameFromToken(token);

        // Assertions
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateInvalidToken() {
        // An invalid token
        String invalidToken = "invalid.token.string";

        // Validate token
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Assertions
        assertFalse(isValid);
    }

    @Test
    void testValidateExpiredToken() {
        // Generate token with a short expiration time
        SecretKey shortLivedKey = Keys.hmacShaKeyFor(secretKeyBase64.getBytes());
        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000))
                .setExpiration(new Date(System.currentTimeMillis() - 500)) // Already expired
                .signWith(shortLivedKey, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();

        // Validate token
        boolean isValid = jwtUtil.validateToken(expiredToken);

        // Assertions
        assertFalse(isValid);
    }
}
