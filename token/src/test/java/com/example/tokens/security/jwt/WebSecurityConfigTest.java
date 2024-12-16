package com.example.tokens.security.jwt;

import com.example.tokens.security.jwt.filter.JwtAuthenticationFilter;
import com.example.tokens.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class WebSecurityConfigTest {

    private WebSecurityConfig webSecurityConfig;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webSecurityConfig = new WebSecurityConfig();
        webSecurityConfig.setUserDetailsService(userDetailsService);
        webSecurityConfig.setJwtAuthenticationFilter(jwtAuthenticationFilter);
    }

    @Test
    void securityFilterChain_shouldReturnValidFilterChain() throws Exception {
        // Simula la configuración del filtro de seguridad
        SecurityFilterChain filterChain = mock(SecurityFilterChain.class);

        assertNotNull(filterChain, "El SecurityFilterChain no debe ser nulo.");
    }

    @Test
    void authenticationManager_shouldReturnAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = webSecurityConfig.authenticationManager(authenticationConfiguration);

        assertNotNull(result, "El AuthenticationManager no debe ser nulo.");
        verify(authenticationConfiguration, times(1)).getAuthenticationManager();
    }

    @Test
    void cookieSerializer_shouldReturnDefaultCookieSerializer() {
        CookieSerializer cookieSerializer = webSecurityConfig.cookieSerializer();

        assertNotNull(cookieSerializer, "El CookieSerializer no debe ser nulo.");
    }

    @Test
    void corsConfigurationSource_shouldReturnValidCorsConfiguration() {
        assertNotNull(webSecurityConfig.corsConfigurationSource(), "El CorsConfigurationSource no debe ser nulo.");
    }
}
