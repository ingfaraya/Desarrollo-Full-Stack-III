package com.example.libro.controller;

import com.example.libro.model.Libro;
import com.example.libro.service.LibroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
public class LibroControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private LibroService libroService;

        @Test
        public void testGetAllLibros() throws Exception {
                Libro libro = new Libro();
                libro.setId(1L);
                libro.setTitulo("Inception");
                libro.setAno("2010");
                libro.setEscritor("Christopher Nolan");
                libro.setGenero("Ciencia Ficción");
                libro.setSinopsis("A thief who steals...");

                List<EntityModel<Libro>> libroModels = Arrays.asList(EntityModel.of(libro,
                                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LibroController.class)
                                                .getLibroById(libro.getId())).withSelfRel()));

                CollectionModel<EntityModel<Libro>> collectionModel = CollectionModel.of(libroModels,
                                WebMvcLinkBuilder.linkTo(
                                                WebMvcLinkBuilder.methodOn(LibroController.class).getAllLibros())
                                                .withSelfRel());

                when(libroService.getAllLibros()).thenReturn(Arrays.asList(libro));
                when(libroService.getLibroById(any())).thenReturn(Optional.of(libro));

                mockMvc.perform(get("/libros"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testGetLibroById() throws Exception {
                Libro libro = new Libro();
                libro.setId(1L);
                libro.setTitulo("Inception");

                when(libroService.getLibroById(1L)).thenReturn(Optional.of(libro));

                mockMvc.perform(get("/libros/1"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testCreateLibro() throws Exception {
                Libro libro = new Libro();
                libro.setId(1L);
                libro.setTitulo("Inception");

                when(libroService.createLibro(any(Libro.class))).thenReturn(libro);

                mockMvc.perform(post("/libros")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"titulo\":\"Inception\",\"ano\":\"2010\",\"escritor\":\"Christopher Nolan\",\"genero\":\"Ciencia Ficción\",\"sinopsis\":\"A thief who steals...\"}"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testUpdateLibro() throws Exception {
                Libro libro = new Libro();
                libro.setId(1L);
                libro.setTitulo("Inception Updated");

                when(libroService.updateLibro(eq(1L), any(Libro.class))).thenReturn(libro);

                mockMvc.perform(put("/libros/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"titulo\":\"Inception Updated\",\"ano\":\"2010\",\"escritor\":\"Christopher Nolan\",\"genero\":\"Ciencia Ficción\",\"sinopsis\":\"A thief who steals...\"}"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testDeleteLibro() throws Exception {
                doNothing().when(libroService).deleteLibro(1L);

                mockMvc.perform(delete("/libros/1"))
                                .andExpect(status().isOk());
        }
}
