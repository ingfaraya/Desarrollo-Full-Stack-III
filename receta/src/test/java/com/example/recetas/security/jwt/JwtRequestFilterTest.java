package com.example.recetas.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new JwtRequestFilter();

        // Generar una clave válida
        String base64Key = "dGhpc2lzYXZlcnlsb25nc2VjcmV0a2V5MTIzNDU2Nzg5MA=="; // Ejemplo clave en Base64
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        secretKey = Keys.hmacShaKeyFor(decodedKey); // Crear SecretKey válida

        jwtRequestFilter.secret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    @Test
    void testDoFilterInternal_ValidToken() throws Exception {
        // Genera un token válido
        String validToken = Jwts.builder()
                .setSubject("testuser")
                .signWith(secretKey)
                .compact();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verifica que el contexto de seguridad se haya actualizado
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(),
                "El contexto de seguridad no debe ser nulo");
        assertEquals("testuser",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                "El principal del contexto debe coincidir con el usuario en el token");

        // Verifica que la cadena de filtros se continúe
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws Exception {
        // No configura un encabezado de autorización
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verifica que el contexto de seguridad no se haya actualizado
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verifica que la cadena de filtros se continúe
        verify(filterChain, times(1)).doFilter(request, response);
    }

}
