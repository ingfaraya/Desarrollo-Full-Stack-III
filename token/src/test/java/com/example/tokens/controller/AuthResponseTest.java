package com.example.tokens.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void testConstructor() {
        // Arrange
        String token = "sampleToken";

        // Act
        AuthResponse authResponse = new AuthResponse(token);

        // Assert
        assertNotNull(authResponse);
        assertEquals(token, authResponse.getToken());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        AuthResponse authResponse = new AuthResponse(null);
        String newToken = "newSampleToken";

        // Act
        authResponse.setToken(newToken);

        // Assert
        assertEquals(newToken, authResponse.getToken());
    }

    public Object getToken() {
        return "generated-jwt-token";
    }
}
