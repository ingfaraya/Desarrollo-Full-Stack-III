import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginComponent } from "./login/login.component";
import { RecetasComponent } from './recetas/recetas.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule, LoginComponent, RecetasComponent],  // Importa RouterModule aquí
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'Hello, frontend';
}
