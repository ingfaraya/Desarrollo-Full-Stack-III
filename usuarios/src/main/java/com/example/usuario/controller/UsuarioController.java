package com.example.usuario.controller;


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
import com.example.usuario.model.Usuario;
import com.example.usuario.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService studentService;

    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = studentService.getAllUsuarios();
        log.info("GET /usuarios");
        log.info("Retornando todas los usuarios");
        List<EntityModel<Usuario>> usuariosResources = usuarios.stream()
            .map( student -> EntityModel.of(student,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(student.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios());
        CollectionModel<EntityModel<Usuario>> resources = CollectionModel.of(usuariosResources, linkTo.withRel("usuarios"));

        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> student = studentService.getUsuarioById(id);
        log.info("GET /usuarios/id/"+id);
        log.info("Retornando usuario id: "+id);
        if (student.isPresent()) {
            return EntityModel.of(student.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios()).withRel("all-usuarios"));
        } else {
            log.error("Usuario not found with id: " + id);
            throw new UsuarioNotFoundException("Usuario not found with id: " + id);
            
        }
    }

    @PostMapping
    public EntityModel<Usuario> createUsuario(@Validated @RequestBody Usuario student) {
        Usuario createdUsuario = studentService.createUsuario(student);
        log.info("POST /usuarios");
        log.info("usuario creada con el id: "+createdUsuario.getId());
            return EntityModel.of(createdUsuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(createdUsuario.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios()).withRel("all-usuarios"));

    }

    @PutMapping("/{id}")
    public EntityModel<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario student) {
        Usuario updatedUsuario = studentService.updateUsuario(id, student);
        log.info("PUT /usuarios/id/"+id);
        log.info("usuario actualizada con el id: "+id);
        return EntityModel.of(updatedUsuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios()).withRel("all-usuarios"));

    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id){
        studentService.deleteUsuario(id);
        log.info("DELETE /usuarios/id/"+id);
        log.info("usuario borrada con el id: "+id);
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
