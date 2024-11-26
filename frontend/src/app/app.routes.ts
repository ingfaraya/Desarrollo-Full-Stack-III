import { Routes } from '@angular/router';
import { RecetasComponent } from './recetas/recetas.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'recetas', pathMatch: 'full' },
  { path: 'recetas', component: RecetasComponent },
  { path: 'login', component: LoginComponent }
];
