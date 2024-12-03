// src/app/recetas/recetas.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RecetasService } from '../services/recetas.service';
import { Receta } from '../models/receta.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-recetas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './recetas.component.html'
})
export class RecetasComponent {
  recetas: Receta[] = [];
  recetaForm: Receta = { 
    nombre: '', 
    ingredientes: '', 
    instrucciones: '', 
    tipoCocina: '', 
    paisOrigen: '', 
    tiempoCoccion: '', 
    dificultad: '', 
    usuario: '' 
  };
  recetaSeleccionada: Receta | null = null;
  mostrarFormulario: boolean = false;

  constructor(readonly recetasService: RecetasService, readonly router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      // Si no hay token, redirige al login
      this.router.navigate(['/login']);
    } else {
      // Si el token está presente, procede a cargar las recetas
      this.obtenerRecetas();
    }
  }

  obtenerRecetas(): void {
    this.recetasService.obtenerRecetas().subscribe({
      next: (response) => {
        this.recetas = response;
      },
      error: (error) => {
        console.error('Error al obtener recetas:', error.message);
        if (error.message === 'Debe iniciar sesión') {
          this.cerrarSesion();
        }
      }
    });
  }

  agregarReceta(): void {
    if (!this.recetaSeleccionada) {
      this.recetasService.agregarReceta(this.recetaForm).subscribe({
        next: () => {
          this.obtenerRecetas();
          this.resetFormulario();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al agregar receta:', error.message);
          if (error.message === 'Debe iniciar sesión') {
            this.cerrarSesion();
          }
        }
      });
    } else {
      this.actualizarReceta();
    }
  }

  editarReceta(receta: Receta): void {
    this.recetaSeleccionada = { ...receta };
    this.recetaForm = { ...receta };
    this.mostrarFormulario = true;
  }

  actualizarReceta(): void {
    if (this.recetaSeleccionada) {
      this.recetasService.actualizarReceta(this.recetaSeleccionada.id!, this.recetaForm).subscribe({
        next: () => {
          this.obtenerRecetas();
          this.resetFormulario();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al actualizar receta:', error.message);
          if (error.message === 'Debe iniciar sesión') {
            this.cerrarSesion();
          }
        }
      });
    }
  }

  eliminarReceta(id: number): void {
    this.recetasService.eliminarReceta(id).subscribe({
      next: () => {
        this.obtenerRecetas();
      },
      error: (error) => {
        console.error('Error al eliminar receta:', error.message);
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

  mostrarAgregarReceta(): void {
    this.resetFormulario();
    this.mostrarFormulario = true;
  }

  cancelarAgregarReceta(): void {
    this.resetFormulario();
    this.mostrarFormulario = false;
  }

  resetFormulario(): void {
    this.recetaSeleccionada = null;
    this.recetaForm = { 
      nombre: '', 
      ingredientes: '', 
      instrucciones: '', 
      tipoCocina: '', 
      paisOrigen: '', 
      tiempoCoccion: '', 
      dificultad: '', 
      usuario: '' 
    };
  }

  compartirReceta(receta: any): void {
    const url = encodeURIComponent(`http://localhost/recetas/${receta.id}`);
    const text = encodeURIComponent(`Mira esta increíble receta de ${receta.nombre} que encontré:`);
    
    const twitterShare = `https://twitter.com/intent/tweet?text=${text}&url=${url}`;
    const facebookShare = `https://www.facebook.com/sharer/sharer.php?u=${url}`;
    const whatsappShare = `https://wa.me/?text=${text}%20${url}`; // Usa %20 para asegurar codificación de espacios
  
    const socialMedia = window.prompt(
      '¿Dónde quieres compartir?\n1. Twitter\n2. Facebook\n3. WhatsApp',
      '1'
    );
  
    switch (socialMedia) {
      case '1':
        window.open(twitterShare, '_blank');
        break;
      case '2':
        window.open(facebookShare, '_blank');
        break;
      case '3':
        window.open(whatsappShare, '_blank');
        break;
      default:
        alert('Opción no válida');
    }
  }  
  
}
