package com.example.recetas.exception;

import org.junit.jupiter.api.Test;

import com.example.recetas.exception.RecetaNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class RecetaNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Receta no encontrada";
        RecetaNotFoundException exception = new RecetaNotFoundException(message);

        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(message, exception.getMessage(), "El mensaje de la excepción debería coincidir con el proporcionado");
    }

    @Test
    void testExceptionWithoutMessage() {
        RecetaNotFoundException exception = new RecetaNotFoundException(null);

        assertNotNull(exception, "La excepción no debería ser nula incluso sin mensaje");
        assertNull(exception.getMessage(), "El mensaje debería ser nulo si no se proporciona");
    }
}