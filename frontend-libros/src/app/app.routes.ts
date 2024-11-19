import { Routes } from '@angular/router';
import { LibrosComponent } from './libros/libros.component';
import { RecetasComponent } from './recetas/recetas.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'libros', pathMatch: 'full' },
  { path: '', redirectTo: 'recetas', pathMatch: 'full' },
  { path: 'recetas', component: RecetasComponent },
  { path: 'libros', component: LibrosComponent },
  { path: 'login', component: LoginComponent }
];
