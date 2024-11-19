package com.example.recetas.controller;

import com.example.recetas.model.Receta;
import com.example.recetas.service.RecetaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // Requiere autenticación para obtener todos los recetas
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Receta>> getAllRecetas() {
        return ResponseEntity.ok(recetaService.getAllRecetas());
    }

    // Requiere autenticación para obtener un receta por ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Receta>> getRecetaById(@PathVariable Long id) {
        return ResponseEntity.ok(recetaService.getRecetaById(id));
    }

    // Requiere autenticación para crear un receta
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Receta> createReceta(@RequestBody Receta receta) {
        return ResponseEntity.ok(recetaService.createReceta(receta));
    }

    // Requiere autenticación para actualizar un receta
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<Receta> updateReceta(@PathVariable Long id, @RequestBody Receta receta) {
        return ResponseEntity.ok(recetaService.updateReceta(id, receta));
    }

    // Requiere autenticación para eliminar un receta
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        recetaService.deleteReceta(id);
        return ResponseEntity.noContent().build();
    }
}
