package com.example.producto.model;

import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "productos")
public class Producto extends RepresentationModel<Producto> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "No puede ingresar un sku de producto vacío")
    @Size(max = 20, message = "El sku del producto no debe exceder los 20 caracteres")
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @NotBlank(message = "No puede ingresar un nombre de producto vacío")
    @Size(max = 50, message = "El nombre de producto no debe exceder los 50 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "No puede ingresar una descripcion de producto vacío")
    @Size(max = 100, message = "La descripcion del producto no debe exceder los 100 caracteres")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotBlank(message = "No puede ingresar un precio de producto vacío")
    @Size(max = 10, message = "El precio del producto no debe exceder los 10 caracteres")
    @Column(name = "precio", nullable = false)
    private String precio;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
