// src/app/libros/libros.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LibrosService } from '../services/libros.service';
import { Libro } from '../models/libro.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-libros',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './libros.component.html',
  styleUrls: ['./libros.component.scss']
})
export class LibrosComponent {
  libros: Libro[] = [];
  libroForm: Libro = { titulo: '', escritor: '', ano: '', genero: '', sinopsis: '' };
  libroSeleccionado: Libro | null = null;
  mostrarFormulario: boolean = false;

  constructor(private libroService: LibrosService, private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      // Si no hay token, redirige al login
      this.router.navigate(['/login']);
    } else {
      // Si el token está presente, procede a cargar los libros
      this.obtenerLibros();
    }
  }

  obtenerLibros(): void {
    this.libroService.obtenerLibros().subscribe({
      next: (response) => {
        this.libros = response;
      },
      error: (error) => {
        console.error('Error al obtener libros:', error.message);
        if (error.message === 'Debe iniciar sesión') {
          this.cerrarSesion();
        }
      }
    });
  }

  agregarLibro(): void {
    if (!this.libroSeleccionado) {
      this.libroService.agregarLibro(this.libroForm).subscribe({
        next: () => {
          this.obtenerLibros();
          this.resetFormulario();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al agregar libro:', error.message);
          if (error.message === 'Debe iniciar sesión') {
            this.cerrarSesion();
          }
        }
      });
    } else {
      this.actualizarLibro();
    }
  }

  editarLibro(libro: Libro): void {
    this.libroSeleccionado = { ...libro };
    this.libroForm = { ...libro };
    this.mostrarFormulario = true;
  }

  actualizarLibro(): void {
    if (this.libroSeleccionado) {
      this.libroService.actualizarLibro(this.libroSeleccionado.id!, this.libroForm).subscribe({
        next: () => {
          this.obtenerLibros();
          this.resetFormulario();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al actualizar libro:', error.message);
          if (error.message === 'Debe iniciar sesión') {
            this.cerrarSesion();
          }
        }
      });
    }
  }

  eliminarLibro(id: number): void {
    this.libroService.eliminarLibro(id).subscribe({
      next: () => {
        this.obtenerLibros();
      },
      error: (error) => {
        console.error('Error al eliminar libro:', error.message);
        if (error.message === 'Debe iniciar sesión') {
          this.cerrarSesion();
        }
      }
    });
  }

  cerrarSesion(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }

  mostrarAgregarLibro(): void {
    this.resetFormulario();
    this.mostrarFormulario = true;
  }

  cancelarAgregarLibro(): void {
    this.resetFormulario();
    this.mostrarFormulario = false;
  }

  resetFormulario(): void {
    this.libroSeleccionado = null;
    this.libroForm = { titulo: '', escritor: '', ano: '', genero: '', sinopsis: '' };
  }
}
