package com.example.tokens.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testDefaultConstructor() {
        // Act
        AuthRequest authRequest = new AuthRequest();

        // Assert
        assertNotNull(authRequest);
        assertNull(authRequest.getUsername());
        assertNull(authRequest.getPassword());
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        // Act
        AuthRequest authRequest = new AuthRequest(username, password);

        // Assert
        assertNotNull(authRequest);
        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        AuthRequest authRequest = new AuthRequest();
        String username = "anotherUser";
        String password = "anotherPassword";

        // Act
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        // Assert
        assertEquals(username, authRequest.getUsername());
        assertEquals(password, authRequest.getPassword());
    }
}
