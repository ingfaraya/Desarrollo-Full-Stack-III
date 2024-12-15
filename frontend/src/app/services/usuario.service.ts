// src/app/services/usuario.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  readonly apiUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  // Obtener token del localStorage
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if (!token) {
      throw new Error('Debe iniciar sesión');
    }
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  // Obtener la lista de usuarios
  obtenerUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al obtener usuarios:', error);
        return throwError(() => new Error('Debe iniciar sesión o hubo un problema al obtener usuarios.'));
      })
    );
  }

  // Crear un nuevo usuario
  agregarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al agregar usuario:', error);
        return throwError(() => error);
      })
    );
  }

  // Actualizar un usuario existente
  actualizarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(this.apiUrl, usuario, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al actualizar usuario:', error);
        return throwError(() => error);
      })
    );
  }
}
