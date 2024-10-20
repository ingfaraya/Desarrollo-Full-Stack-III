package com.example.libro.service;

import com.example.libro.model.Libro;
import com.example.libro.repository.LibroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllLibros() {
        Libro libro1 = new Libro();
        libro1.setTitulo("Inception");
        Libro libro2 = new Libro();
        libro2.setTitulo("Interstellar");

        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        List<Libro> libros = libroService.getAllLibros();
        assertEquals(2, libros.size());
        verify(libroRepository).findAll();
    }

    @Test
    void shouldReturnLibroById() {
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Inception");
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Optional<Libro> result = libroService.getLibroById(1L);
        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getTitulo());
    }

    @Test
    void shouldCreateLibro() {
        Libro libro = new Libro();
        libro.setTitulo("Inception");
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro savedLibro = libroService.createLibro(libro);
        assertNotNull(savedLibro);
        assertEquals("Inception", savedLibro.getTitulo());
    }

    @Test
    void shouldUpdateLibro() {
        Long libroId = 1L;
        Libro existingLibro = new Libro();
        existingLibro.setId(libroId);
        existingLibro.setTitulo("Inception");
        existingLibro.setAno("2010");
        existingLibro.setEscritor("Christopher Nolan");
        existingLibro.setGenero("Ciencia Ficción");
        existingLibro.setSinopsis("A thief who steals...");

        // Mockeo para asegurar que la película existe
        when(libroRepository.existsById(libroId)).thenReturn(true);
        when(libroRepository.findById(libroId)).thenReturn(Optional.of(existingLibro));
        when(libroRepository.save(any(Libro.class))).thenReturn(existingLibro);

        // Llamada al método de servicio
        Libro updatedLibro = libroService.updateLibro(libroId, existingLibro);

        assertNotNull(updatedLibro);
        assertEquals("Inception", updatedLibro.getTitulo());

        // Verifica que los métodos del repositorio se llamaron
        verify(libroRepository).findById(libroId);
        verify(libroRepository).save(existingLibro);
    }

    @Test
    void shouldDeleteLibro() {
        doNothing().when(libroRepository).deleteById(1L);
        libroService.deleteLibro(1L);
        verify(libroRepository).deleteById(1L);
    }
}
