package com.example.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
