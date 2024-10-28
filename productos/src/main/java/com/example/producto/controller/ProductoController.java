package com.example.producto.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.producto.model.Producto;
import com.example.producto.service.ProductoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/productos")
public class ProductoController {


    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService studentService;

    @GetMapping
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<Producto> productos = studentService.getAllProductos();
        log.info("GET /productos");
        log.info("Retornando todas los productos");
        List<EntityModel<Producto>> productosResources = productos.stream()
            .map( student -> EntityModel.of(student,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(student.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos());
        CollectionModel<EntityModel<Producto>> resources = CollectionModel.of(productosResources, linkTo.withRel("productos"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> student = studentService.getProductoById(id);
        log.info("GET /productos/id/"+id);
        log.info("Retornando producto id: "+id);
        if (student.isPresent()) {
            return EntityModel.of(student.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos()).withRel("all-productos"));
        } else {
            log.error("Producto not found with id: " + id);
            throw new ProductoNotFoundException("Producto not found with id: " + id);
            
        }
    }

    @PostMapping
    public EntityModel<Producto> createProducto(@Validated @RequestBody Producto student) {
        Producto createdProducto = studentService.createProducto(student);
        log.info("POST /productos");
        log.info("producto creada con el id: "+createdProducto.getId());
            return EntityModel.of(createdProducto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(createdProducto.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos()).withRel("all-productos"));

    }

    @PutMapping("/{id}")
    public EntityModel<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto student) {
        Producto updatedProducto = studentService.updateProducto(id, student);
        log.info("PUT /productos/id/"+id);
        log.info("producto actualizada con el id: "+id);
        return EntityModel.of(updatedProducto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductoById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProductos()).withRel("all-productos"));

    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id){
        studentService.deleteProducto(id);
        log.info("DELETE /productos/id/"+id);
        log.info("producto borrada con el id: "+id);
    }


    static class ErrorResponse {
        private final String message;
    
        public ErrorResponse(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    }


}
