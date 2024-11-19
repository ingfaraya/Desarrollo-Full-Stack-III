package com.example.recetas.service;

import com.example.recetas.model.Receta;
import java.util.List;
import java.util.Optional;

public interface RecetaService {
    List<Receta> getAllRecetas();
    Optional<Receta> getRecetaById(Long id);
    Receta createReceta(Receta receta);
    Receta updateReceta(Long id, Receta receta);
    void deleteReceta(Long id);
}
