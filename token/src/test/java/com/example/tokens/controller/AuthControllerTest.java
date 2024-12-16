package com.example.tokens.controller;

import com.example.tokens.security.jwt.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String token = "generated-jwt-token";

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateToken(username)).thenReturn(token);

        // Act
        AuthResponse response = authController.login(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(username);
    }

    @Test
    void login_shouldThrowException_whenCredentialsAreInvalid() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid username or password"));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> authController.login(authRequest));
        assertEquals("Invalid username or password", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }
}
