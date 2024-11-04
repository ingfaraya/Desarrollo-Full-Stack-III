// src/app/services/libros.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Libro } from '../models/libro.model';

@Injectable({
  providedIn: 'root'
})
export class LibrosService {
  private apiUrl = 'http://localhost:8081/api/libros';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if (!token) {
      throw new Error('Debe iniciar sesión');
    }
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  obtenerLibros(): Observable<Libro[]> {
    return this.http.get<Libro[]>(this.apiUrl, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al obtener libros:', error);
        return throwError(() => new Error('Debe iniciar sesión'));
      })
    );
  }

  agregarLibro(libro: Libro): Observable<Libro> {
    return this.http.post<Libro>(this.apiUrl, libro, {
      headers: this.getHeaders()
    });
  }

  actualizarLibro(id: number, libro: Libro): Observable<Libro> {
    return this.http.put<Libro>(`${this.apiUrl}/${id}`, libro, {
      headers: this.getHeaders()
    });
  }

  eliminarLibro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }
}
