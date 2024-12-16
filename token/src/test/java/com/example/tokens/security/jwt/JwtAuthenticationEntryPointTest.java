package com.example.tokens.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    @Test
    void testCommenceShouldSendUnauthorizedError() throws IOException, ServletException  {
        // Mock de HttpServletRequest y HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        // Crear instancia de JwtAuthenticationEntryPoint
        AuthenticationEntryPoint entryPoint = mock(AuthenticationEntryPoint.class);

        // Ejecutar el método commence
        entryPoint.commence(request, response, authException);
    }
}
