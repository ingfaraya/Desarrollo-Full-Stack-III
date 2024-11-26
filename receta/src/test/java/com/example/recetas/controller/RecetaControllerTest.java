package com.example.recetas.controller;

import com.example.recetas.model.Receta;
import com.example.recetas.service.RecetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecetaControllerTest {

    @Mock
    private RecetaService recetaService;

    @InjectMocks
    private RecetaController recetaController;

    private Receta receta1;
    private Receta receta2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear datos de prueba
        receta1 = new Receta();
        receta1.setId(1L);
        receta1.setNombre("Receta 1");

        receta2 = new Receta();
        receta2.setId(2L);
        receta2.setNombre("Receta 2");
    }

    @Test
    void testGetAllRecetas() {
        // Configurar comportamiento simulado
        when(recetaService.getAllRecetas()).thenReturn(Arrays.asList(receta1, receta2));

        // Llamar al método y verificar la respuesta
        ResponseEntity<List<Receta>> response = recetaController.getAllRecetas();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(recetaService, times(1)).getAllRecetas();
    }

    @Test
    void testGetRecetaById() {
        // Configurar comportamiento simulado
        when(recetaService.getRecetaById(1L)).thenReturn(Optional.of(receta1));

        // Llamar al método y verificar la respuesta
        ResponseEntity<Optional<Receta>> response = recetaController.getRecetaById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Receta 1", response.getBody().get().getNombre());
        verify(recetaService, times(1)).getRecetaById(1L);
    }

    @Test
    void testCreateReceta() {
        // Configurar comportamiento simulado
        when(recetaService.createReceta(any(Receta.class))).thenReturn(receta1);

        // Llamar al método y verificar la respuesta
        ResponseEntity<Receta> response = recetaController.createReceta(receta1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Receta 1", response.getBody().getNombre());
        verify(recetaService, times(1)).createReceta(any(Receta.class));
    }

    @Test
    void testUpdateReceta() {
        // Configurar comportamiento simulado
        when(recetaService.updateReceta(eq(1L), any(Receta.class))).thenReturn(receta1);

        // Llamar al método y verificar la respuesta
        ResponseEntity<Receta> response = recetaController.updateReceta(1L, receta1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Receta 1", response.getBody().getNombre());
        verify(recetaService, times(1)).updateReceta(eq(1L), any(Receta.class));
    }

    @Test
    void testDeleteReceta() {
        // Llamar al método
        ResponseEntity<Void> response = recetaController.deleteReceta(1L);

        // Verificar respuesta y llamadas al servicio
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(recetaService, times(1)).deleteReceta(1L);
    }
}
