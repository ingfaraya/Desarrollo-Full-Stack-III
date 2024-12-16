package com.example.recetas.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.naming.AuthenticationException;

import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private org.springframework.security.core.AuthenticationException authException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void testCommence() throws IOException, ServletException {
        // Simula el comportamiento de la respuesta
        doNothing().when(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No tiene acceso a este recurso");

        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Verifica que el método sendError se haya llamado con los parámetros correctos
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No tiene acceso a este recurso");
    }
}
