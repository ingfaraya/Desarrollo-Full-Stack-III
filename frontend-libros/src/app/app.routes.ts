import { Routes } from '@angular/router';
import { LibrosComponent } from './libros/libros.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'libros', pathMatch: 'full' },
  { path: 'libros', component: LibrosComponent },
  { path: 'login', component: LoginComponent }
];
