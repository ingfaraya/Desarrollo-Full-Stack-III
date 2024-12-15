import { Routes } from '@angular/router';
import { RecetasComponent } from './recetas/recetas.component';
import { LoginComponent } from './login/login.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'recetas', component: RecetasComponent },
  { path: 'usuarios', component: UsuariosComponent },
  { path: 'login', component: LoginComponent }
];
