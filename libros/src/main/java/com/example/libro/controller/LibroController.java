package com.example.libro.controller;


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
import com.example.libro.model.Libro;
import com.example.libro.service.LibroService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/libros")
public class LibroController {


    private static final Logger log = LoggerFactory.getLogger(LibroController.class);

    @Autowired
    private LibroService studentService;

    @GetMapping
    public CollectionModel<EntityModel<Libro>> getAllLibros() {
        List<Libro> libros = studentService.getAllLibros();
        log.info("GET /libros");
        log.info("Retornando todas los libros");
        List<EntityModel<Libro>> librosResources = libros.stream()
            .map( student -> EntityModel.of(student,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibroById(student.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllLibros());
        CollectionModel<EntityModel<Libro>> resources = CollectionModel.of(librosResources, linkTo.withRel("libros"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> student = studentService.getLibroById(id);
        log.info("GET /libros/id/"+id);
        log.info("Retornando libro id: "+id);
        if (student.isPresent()) {
            return EntityModel.of(student.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibroById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllLibros()).withRel("all-libros"));
        } else {
            log.error("Libro not found with id: " + id);
            throw new LibroNotFoundException("Libro not found with id: " + id);
            
        }
    }

    @PostMapping
    public EntityModel<Libro> createLibro(@Validated @RequestBody Libro student) {
        Libro createdLibro = studentService.createLibro(student);
        log.info("POST /libros");
        log.info("libro creada con el id: "+createdLibro.getId());
            return EntityModel.of(createdLibro,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibroById(createdLibro.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllLibros()).withRel("all-libros"));

    }

    @PutMapping("/{id}")
    public EntityModel<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro student) {
        Libro updatedLibro = studentService.updateLibro(id, student);
        log.info("PUT /libros/id/"+id);
        log.info("libro actualizada con el id: "+id);
        return EntityModel.of(updatedLibro,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibroById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllLibros()).withRel("all-libros"));

    }

    @DeleteMapping("/{id}")
    public void deleteLibro(@PathVariable Long id){
        studentService.deleteLibro(id);
        log.info("DELETE /libros/id/"+id);
        log.info("libro borrada con el id: "+id);
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
