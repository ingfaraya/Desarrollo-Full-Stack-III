import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { Usuario } from '../models/usuario.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.component.html'
})
export class UsuariosComponent {
  usuarios: Usuario[] = [];
  usuarioForm: Usuario = { username: '', password: '', role: '' };
  usuarioSeleccionado: Usuario | null = null;
  mostrarFormulario: boolean = false;

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.router.navigate(['/login']);
    } else {
      this.obtenerUsuarios();
    }
  }

  obtenerUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (response) => {
        this.usuarios = response;
      },
      error: (error) => {
        console.error('Error al obtener usuarios:', error.message);
        this.cerrarSesion();
      }
    });
  }

  agregarUsuario(): void {
    if (!this.usuarioSeleccionado) {
      this.usuarioService.agregarUsuario(this.usuarioForm).subscribe({
        next: () => {
          this.obtenerUsuarios();
          this.resetFormulario();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al agregar usuario:', error.message);
        }
      });
    } else {
      this.actualizarUsuario();
    }
  }

  editarUsuario(usuario: Usuario): void {
    this.usuarioSeleccionado = { ...usuario };
    this.usuarioForm = { ...usuario, password: '' }; // Limpiar la contraseña
    this.mostrarFormulario = true;
  }

  actualizarUsuario(): void {
    if (this.usuarioSeleccionado) {
      this.usuarioService.actualizarUsuario(this.usuarioForm).subscribe({
        next: () => {
          this.obtenerUsuarios();
          this.resetFormulario();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al actualizar usuario:', error.message);
        }
      });
    }
  }

  cerrarSesion(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }

  volverHome(): void {
    this.router.navigate(['/home'])
  }

  mostrarAgregarUsuario(): void {
    this.resetFormulario();
    this.mostrarFormulario = true;
  }

  cancelarAgregarUsuario(): void {
    this.resetFormulario();
    this.mostrarFormulario = false;
  }

  resetFormulario(): void {
    this.usuarioSeleccionado = null;
    this.usuarioForm = { username: '', password: '', role: '' };
  }
}
