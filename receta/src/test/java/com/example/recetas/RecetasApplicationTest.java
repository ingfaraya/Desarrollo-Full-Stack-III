package com.example.recetas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecetasApplicationTest {

    @Test
    void contextLoads() {
        // Si el contexto no carga, esta prueba fallará automáticamente
    }

    @Test
    void testMainMethod() {
        // Ejecutar el método main y verificar que no lanza excepciones
        String[] args = {};
        RecetasApplication.main(args);
    }
}
