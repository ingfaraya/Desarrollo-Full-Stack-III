package com.example.recetas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recetas.model.Receta;
import com.example.recetas.repository.RecetaRepository;
import com.example.recetas.exception.RecetaNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public List<Receta> getAllRecetas() {
        return recetaRepository.findAll();
    }

    @Override
    public Optional<Receta> getRecetaById(Long id) {
        return recetaRepository.findById(id);
    }

    @Override
    public Receta createReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public Receta updateReceta(Long id, Receta receta) {
        return recetaRepository.findById(id)
            .map(existingReceta -> {
                receta.setId(id);
                return recetaRepository.save(receta);
            })
            .orElseThrow(() -> new RecetaNotFoundException("Receta not found with id: " + id));
    }

    @Override
    public void deleteReceta(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new RecetaNotFoundException("Receta not found with id: " + id);
        }
        recetaRepository.deleteById(id);
    }
}
