package com.example.libro.repository;

import com.example.libro.model.Libro;

import jakarta.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LibroRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LibroRepository libroRepository;

    @Test
    public void testFindById() {
        // Given
        Libro libro = new Libro();
        libro.setTitulo("Inception");
        libro.setAno("2010");
        libro.setEscritor("Christopher Nolan");
        libro.setGenero("Ciencia Ficción");
        libro.setSinopsis("Un ladrón que...");
        libro = entityManager.persistFlushFind(libro);

        // When
        Optional<Libro> found = libroRepository.findById(libro.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("Inception", found.get().getTitulo());
    }

    @Test
    public void testSave() {
        // Given
        Libro libro = new Libro();
        libro.setTitulo("Interstellar");
        libro.setAno("2014");
        libro.setEscritor("Christopher Nolan");
        libro.setGenero("Aventura Espacial");
        libro.setSinopsis("Aventuras en el espacio...");

        // When
        Libro saved = libroRepository.save(libro);

        // Then
        assertNotNull(saved.getId());
        assertEquals("Interstellar", saved.getTitulo());
    }

    @Test
    public void testDelete() {
        // Given
        Libro libro = new Libro();
        libro.setTitulo("Inception");
        libro.setAno("2014");
        libro.setEscritor("Christopher Nolan");
        libro.setGenero("Aventura Espacial");
        libro.setSinopsis("Aventuras en el espacio...");
        libro = entityManager.persistFlushFind(libro);

        // When
        libroRepository.delete(libro);

        // Then
        Optional<Libro> deleted = libroRepository.findById(libro.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    public void testFindAll() {
        // Given
        Libro libro1 = new Libro();
        libro1.setTitulo("Inception");
        libro1.setAno("2014");
        libro1.setEscritor("Christopher Nolan");
        libro1.setGenero("Aventura Espacial");
        libro1.setSinopsis("Aventuras en el espacio...");
        entityManager.persist(libro1);

        Libro libro2 = new Libro();
        libro2.setTitulo("Interstellar");
        libro2.setAno("2014");
        libro2.setEscritor("Christopher Nolan");
        libro2.setGenero("Aventura Espacial");
        libro2.setSinopsis("Aventuras en el espacio...");
        entityManager.persist(libro2);

        entityManager.flush();

        // When
        List<Libro> libros = libroRepository.findAll();

        // Then
        assertEquals(9, libros.size());
    }

    @Test
    public void testSaveInvalidLibro() {
        Libro libro = new Libro();
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            libroRepository.saveAndFlush(libro);
        });

        String expectedMessage = "No puede ingresar un escritor vacio";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
