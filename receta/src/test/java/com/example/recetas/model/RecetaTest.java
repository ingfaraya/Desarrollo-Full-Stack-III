package com.example.recetas.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.recetas.model.Receta;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecetaTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidReceta() {
        Receta receta = new Receta();
        receta.setNombre("Paella");
        receta.setIngredientes("Arroz, mariscos, azafrán");
        receta.setInstrucciones("Cocinar a fuego medio por 30 minutos");
        receta.setTipoCocina("Española");
        receta.setPaisOrigen("España");
        receta.setTiempoCoccion("30 minutos");
        receta.setDificultad("Media");
        receta.setUsuario("usuario123");

        Set<ConstraintViolation<Receta>> violations = validator.validate(receta);
        assertTrue(violations.isEmpty(), "No debería haber violaciones en una receta válida");
    }

    @Test
    void testInvalidReceta() {
        Receta receta = new Receta(); // No se establecen valores para probar las validaciones

        Set<ConstraintViolation<Receta>> violations = validator.validate(receta);

        assertFalse(violations.isEmpty(), "Debería haber violaciones para una receta inválida");
        assertEquals(8, violations.size(), "Debería haber 8 violaciones por los campos faltantes");
    }
}