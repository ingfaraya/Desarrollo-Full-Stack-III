import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(readonly http: HttpClient, readonly router: Router) {}

  login() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post<{ token: string }>('http://localhost:8080/auth/token', loginData).subscribe({
      next: (response) => {
        localStorage.setItem('authToken', response.token);
        console.log('Login exitoso, redirigiendo a recetas...');
        this.router.navigate(['/recetas']);
      },
      error: (error) => {
        console.error('Error de login:', error);
        alert('Nombre de usuario o contraseña incorrectos');
      }
    });
  }
}
