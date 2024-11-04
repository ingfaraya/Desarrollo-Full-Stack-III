package com.example.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libros.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    
}
