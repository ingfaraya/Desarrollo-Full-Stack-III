package com.example.recetas.service;

import com.example.recetas.exception.RecetaNotFoundException;
import com.example.recetas.model.Receta;
import com.example.recetas.repository.RecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecetaServiceImplTest {

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private RecetaServiceImpl recetaService;

    private Receta receta1;
    private Receta receta2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        receta1 = new Receta();
        receta1.setId(1L);
        receta1.setNombre("Receta 1");
        receta2 = new Receta();
        receta2.setId(2L);
        receta2.setNombre("Receta 2");
    }

    @Test
    void testGetAllRecetas() {
        List<Receta> recetas = Arrays.asList(receta1, receta2);
        when(recetaRepository.findAll()).thenReturn(recetas);

        List<Receta> result = recetaService.getAllRecetas();

        assertEquals(2, result.size());
        verify(recetaRepository, times(1)).findAll();
    }

    @Test
    void testGetRecetaById_Success() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta1));

        Optional<Receta> result = recetaService.getRecetaById(1L);

        assertTrue(result.isPresent());
        assertEquals("Receta 1", result.get().getNombre());
        verify(recetaRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRecetaById_NotFound() {
        when(recetaRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Receta> result = recetaService.getRecetaById(3L);

        assertFalse(result.isPresent());
        verify(recetaRepository, times(1)).findById(3L);
    }

    @Test
    void testCreateReceta() {
        when(recetaRepository.save(receta1)).thenReturn(receta1);

        Receta result = recetaService.createReceta(receta1);

        assertNotNull(result);
        assertEquals("Receta 1", result.getNombre());
        verify(recetaRepository, times(1)).save(receta1);
    }

    @Test
    void testUpdateReceta_Success() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta1));
        when(recetaRepository.save(receta1)).thenReturn(receta1);

        Receta result = recetaService.updateReceta(1L, receta1);

        assertNotNull(result);
        assertEquals("Receta 1", result.getNombre());
        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(receta1);
    }

    @Test
    void testUpdateReceta_NotFound() {
        when(recetaRepository.findById(3L)).thenReturn(Optional.empty());

        RecetaNotFoundException exception = assertThrows(RecetaNotFoundException.class, () -> {
            recetaService.updateReceta(3L, receta1);
        });

        assertEquals("Receta not found with id: 3", exception.getMessage());
        verify(recetaRepository, times(1)).findById(3L);
        verify(recetaRepository, times(0)).save(any(Receta.class));
    }

    @Test
    void testDeleteReceta_Success() {
        when(recetaRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> recetaService.deleteReceta(1L));

        verify(recetaRepository, times(1)).existsById(1L);
        verify(recetaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReceta_NotFound() {
        when(recetaRepository.existsById(3L)).thenReturn(false);

        RecetaNotFoundException exception = assertThrows(RecetaNotFoundException.class, () -> {
            recetaService.deleteReceta(3L);
        });

        assertEquals("Receta not found with id: 3", exception.getMessage());
        verify(recetaRepository, times(1)).existsById(3L);
        verify(recetaRepository, times(0)).deleteById(anyLong());
    }
}
