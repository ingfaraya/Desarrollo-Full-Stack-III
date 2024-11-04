package com.example.libros.service;

import com.example.libros.model.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> getAllLibros();
    Optional<Libro> getLibroById(Long id);
    Libro createLibro(Libro student);
    Libro updateLibro(Long id,Libro student);
    void deleteLibro(Long id);
}
