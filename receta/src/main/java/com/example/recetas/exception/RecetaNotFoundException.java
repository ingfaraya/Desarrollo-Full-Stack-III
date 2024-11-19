package com.example.recetas.exception;

public class RecetaNotFoundException extends RuntimeException {
    public RecetaNotFoundException(String message) {
        super(message);
    }
}
