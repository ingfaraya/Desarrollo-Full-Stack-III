package com.example.libro.service;

import com.example.libro.model.Libro;
import com.example.libro.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLibrosTest() {
        Libro libro1 = new Libro();
        libro1.setId(1L);
        libro1.setTitulo("Inception");
        libro1.setAno("2010");
        libro1.setEscritor("Christopher Nolan");
        libro1.setGenero("Ciencia Ficción");
        libro1.setSinopsis("Un ladrón...");

        Libro libro2 = new Libro();
        libro2.setId(2L);
        libro2.setTitulo("Interstellar");
        libro2.setAno("2014");
        libro2.setEscritor("Christopher Nolan");
        libro2.setGenero("Aventura Espacial");
        libro2.setSinopsis("Un grupo de exploradores...");

        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        List<Libro> libros = libroService.getAllLibros();
        assertFalse(libros.isEmpty());
        assertEquals(2, libros.size());
        assertEquals("Inception", libros.get(0).getTitulo());
    }

    @Test
    void getLibroByIdTest() {
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Inception");
        libro.setAno("2010");
        libro.setEscritor("Christopher Nolan");
        libro.setGenero("Ciencia Ficción");
        libro.setSinopsis("Un ladrón...");

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Optional<Libro> found = libroService.getLibroById(1L);
        assertTrue(found.isPresent());
        assertEquals("Inception", found.get().getTitulo());
    }

    @Test
    void createLibroTest() {
        Libro libro = new Libro();
        libro.setTitulo("Inception");
        libro.setAno("2010");
        libro.setEscritor("Christopher Nolan");
        libro.setGenero("Ciencia Ficción");
        libro.setSinopsis("Un ladrón...");

        when(libroRepository.save(libro)).thenReturn(libro);

        Libro saved = libroService.createLibro(libro);
        assertNotNull(saved);
        assertEquals("Inception", saved.getTitulo());
    }

    @Test
    void updateLibroTest() {
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
    void updateNonExistentLibroTest() {
        Long libroId = 1L;
        Libro libro = new Libro();

        // Configurar para que no se encuentre la película
        when(libroRepository.existsById(libroId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            libroService.updateLibro(libroId, libro);
        });

        assertEquals("Libro not found with id: 1", exception.getMessage());
    }

    @Test
    void deleteLibroTest() {
        doNothing().when(libroRepository).deleteById(1L);

        libroService.deleteLibro(1L);
        verify(libroRepository, times(1)).deleteById(1L);
    }
}
