package com.example.libros.controller;

import com.example.libros.model.Libro;
import com.example.libros.service.LibroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // Requiere autenticación para obtener todos los libros
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Libro>> getAllLibros() {
        return ResponseEntity.ok(libroService.getAllLibros());
    }

    // Requiere autenticación para obtener un libro por ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Libro>> getLibroById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.getLibroById(id));
    }

    // Requiere autenticación para crear un libro
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Libro> createLibro(@RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.createLibro(libro));
    }

    // Requiere autenticación para actualizar un libro
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.updateLibro(id, libro));
    }

    // Requiere autenticación para eliminar un libro
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }
}
