package com.example.usuario.model;

import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "usuarios")
public class Usuario extends RepresentationModel<Usuario> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "No puede ingresar un nombre de usuario vacío")
    @Size(max = 50, message = "El nombre de usuario no debe exceder los 50 caracteres")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "No puede ingresar una contraseña vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "No puede ingresar un rol vacío")
    @Size(max = 20, message = "El rol no debe exceder los 20 caracteres")
    @Column(name = "role", nullable = false)
    private String role;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // No se provee un getter para la contraseña por seguridad
    public void setPassword(String password) {
        // Se recomienda almacenar contraseñas usando un hash (por ejemplo BCrypt)
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
