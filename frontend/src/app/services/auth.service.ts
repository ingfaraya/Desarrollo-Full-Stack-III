// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  readonly authUrl = 'http://localhost:8080/auth/token';  // URL del backend para obtener el token

  constructor(readonly http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.authUrl, { username, password });
  }
}
