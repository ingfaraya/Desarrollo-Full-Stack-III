package com.example.libros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libros.model.Libro;
import com.example.libros.repository.LibroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService{
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }
    
    @Override
    public Libro createLibro(Libro libro){
        return libroRepository.save(libro);
    }

    @Override
    public Libro updateLibro(Long id, Libro libro) {
        return libroRepository.findById(id)
            .map(existingLibro -> {
                libro.setId(id);
                return libroRepository.save(libro);
            })
            .orElseThrow(() -> new RuntimeException("Libro not found with id: " + id));  // Consider using a custom exception
    }

    @Override
    public void deleteLibro(Long id){
        libroRepository.deleteById(id);
    }
}
