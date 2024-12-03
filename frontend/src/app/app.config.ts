import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { routes } from './app.routes';

export const appConfig = {
  providers: [
    provideRouter(routes),
    importProvidersFrom(HttpClient)  // Importando HttpClientModule
  ]
};
