package com.example.recetas.security;

import com.example.recetas.security.jwt.JwtRequestFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // No es necesario configurar `MockMvc` manualmente si usas `@AutoConfigureMockMvc`.
    }

    @Test
    void testCorsConfiguration() throws Exception {
        WebSecurityConfig securityConfig = new WebSecurityConfig();
        assertThat(securityConfig.corsConfigurationSource()).isNotNull();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testApiRecetasWithAuth() throws Exception {
        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk());
    }

    @Test
    void testApiRecetasWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testPublicEndpoint() throws Exception {
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk());
    }
}
