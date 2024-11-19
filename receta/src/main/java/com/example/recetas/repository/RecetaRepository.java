package com.example.recetas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recetas.model.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long>{
    
}
