package com.example.recetas.model;

import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "recetas")
public class Receta extends RepresentationModel<Receta> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "No puede ingresar un nombre vacío")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "No puede ingresar ingredientes vacíos")
    @Column(name = "ingredientes")
    private String ingredientes;

    @NotBlank(message = "No puede ingresar instrucciones vacías")
    @Column(name = "instrucciones")
    private String instrucciones;

    @NotBlank(message = "No puede ingresar un tipo de cocina vacío")
    @Column(name = "tipo_cocina")
    private String tipoCocina;

    @NotBlank(message = "No puede ingresar un país de origen vacío")
    @Column(name = "pais_origen")
    private String paisOrigen;

    @NotBlank(message = "No puede ingresar un tiempo de cocción vacío")
    @Column(name = "tiempo_coccion")
    private String tiempoCoccion;

    @NotBlank(message = "No puede ingresar una dificultad vacía")
    @Column(name = "dificultad")
    private String dificultad;

    @NotBlank(message = "No puede ingresar un usuario vacío")
    @Column(name = "usuario")
    private String usuario;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getTiempoCoccion() {
        return tiempoCoccion;
    }

    public void setTiempoCoccion(String tiempoCoccion) {
        this.tiempoCoccion = tiempoCoccion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
