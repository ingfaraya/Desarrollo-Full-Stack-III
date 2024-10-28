package com.example.producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.producto.model.Producto;
import com.example.producto.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public Producto createProducto(Producto producto){
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
            .map(existingProducto -> {
                producto.setId(id);
                return productoRepository.save(producto);
            })
            .orElseThrow(() -> new RuntimeException("Producto not found with id: " + id));  // Consider using a custom exception
    }

    @Override
    public void deleteProducto(Long id){
        productoRepository.deleteById(id);
    }
}
