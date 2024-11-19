// src/app/services/recetas.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Receta } from '../models/receta.model';

@Injectable({
  providedIn: 'root'
})
export class RecetasService {
  private apiUrl = 'http://localhost:8082/api/recetas';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if (!token) {
      throw new Error('Debe iniciar sesión');
    }
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  obtenerRecetas(): Observable<Receta[]> {
    return this.http.get<Receta[]>(this.apiUrl, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al obtener recetas:', error);
        return throwError(() => new Error('Debe iniciar sesión'));
      })
    );
  }

  agregarReceta(receta: Receta): Observable<Receta> {
    return this.http.post<Receta>(this.apiUrl, receta, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al agregar receta:', error);
        return throwError(() => error);
      })
    );
  }

  actualizarReceta(id: number, receta: Receta): Observable<Receta> {
    return this.http.put<Receta>(`${this.apiUrl}/${id}`, receta, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al actualizar receta:', error);
        return throwError(() => error);
      })
    );
  }

  eliminarReceta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    }).pipe(
      catchError(error => {
        console.error('Error al eliminar receta:', error);
        return throwError(() => error);
      })
    );
  }
}
