import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RecetasService } from '../services/recetas.service';
import { RecetasComponent } from './recetas.component';
import { of, throwError } from 'rxjs';
import { Receta } from '../models/receta.model';

describe('RecetasComponent', () => {
  let component: RecetasComponent;
  let fixture: any;
  let recetasService: jasmine.SpyObj<RecetasService>;
  let router: Router;

  const mockRecetas = [
    {
      id: 1,
      nombre: 'Receta 1',
      ingredientes: 'Ingrediente 1',
      instrucciones: 'Instrucción 1',
      tipoCocina: 'Italiana',
      paisOrigen: 'Italia',
      tiempoCoccion: '30 min',
      dificultad: 'Fácil',
      usuario: 'User1',
    },
  ];

  beforeEach(async () => {
    const recetasServiceSpy = jasmine.createSpyObj('RecetasService', [
      'obtenerRecetas',
      'agregarReceta',
      'actualizarReceta',
      'eliminarReceta',
    ]);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RecetasComponent],
      providers: [
        {
          provide: Router,
          useValue: { navigate: jasmine.createSpy('navigate') },
        },
        { provide: RecetasService, useValue: recetasServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RecetasComponent);
    component = fixture.componentInstance;
    recetasService = TestBed.inject(RecetasService) as jasmine.SpyObj<RecetasService>;
    router = TestBed.inject(Router);

    // Global mock de localStorage
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'authToken') return 'mockAuthToken';
      return null;
    });

    // Mockear servicios
    recetasService.obtenerRecetas.and.returnValue(of(mockRecetas));
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load recipes on init', () => {
    component.ngOnInit();
    expect(component.recetas.length).toBe(1);
    expect(component.recetas[0].nombre).toBe('Receta 1');
  });

  it('should redirect to login if no token', () => {
    (localStorage.getItem as jasmine.Spy).and.returnValue(null);
    component.ngOnInit();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should reset the form after adding a recipe', () => {
    recetasService.agregarReceta.and.returnValue(of(mockRecetas[0]));

    component.recetaForm = { ...mockRecetas[0] };
    component.agregarReceta();

    expect(component.recetaForm.nombre).toBe('');
    expect(component.recetaSeleccionada).toBeNull();
  });

  it('should handle errors when adding a recipe', () => {
    recetasService.agregarReceta.and.returnValue(throwError(() => new Error('Debe iniciar sesión')));
    spyOn(component, 'cerrarSesion');

    component.agregarReceta();

    expect(component.cerrarSesion).toHaveBeenCalled();
  });

  it('should share a recipe on social media', () => {
    spyOn(window, 'prompt').and.returnValue('1'); // Simula la selección de Twitter
    spyOn(window, 'open');
  
    const expectedUrl = 'https://twitter.com/intent/tweet?text=Mira%20esta%20incre%C3%ADble%20receta%20de%20Receta%201%20que%20encontr%C3%A9:&url=http%3A%2F%2Flocalhost%2Frecetas%2F1';
  
    component.compartirReceta(mockRecetas[0]);
  
    // Normalizamos la URL generada para evitar errores de comparación por codificación
    const [actualUrl] = (window.open as jasmine.Spy).calls.mostRecent().args;
    expect(decodeURIComponent(actualUrl)).toEqual(decodeURIComponent(expectedUrl));
  });  

  it('should share a recipe on social media', () => {
    spyOn(window, 'prompt').and.returnValue('1'); // Simula la selección de Twitter
    spyOn(window, 'open');
  
    const expectedUrl = 'https://twitter.com/intent/tweet?text=Mira%20esta%20incre%C3%ADble%20receta%20de%20Receta%201%20que%20encontr%C3%A9:&url=http%3A%2F%2Flocalhost%2Frecetas%2F1';
  
    component.compartirReceta(mockRecetas[0]);
  
    // Normalizamos la URL generada para evitar errores de comparación por codificación
    const [actualUrl] = (window.open as jasmine.Spy).calls.mostRecent().args;
    expect(decodeURIComponent(actualUrl)).toEqual(decodeURIComponent(expectedUrl));
  });
  
  it('should log out and redirect to login', () => {
    spyOn(localStorage, 'removeItem');
  
    component.cerrarSesion();
  
    expect(localStorage.removeItem).toHaveBeenCalledWith('authToken');
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
  
  it('should show add recipe form', () => {
    component.mostrarAgregarReceta();
  
    expect(component.mostrarFormulario).toBeTrue();
    expect(component.recetaForm).toEqual({
      nombre: '',
      ingredientes: '',
      instrucciones: '',
      tipoCocina: '',
      paisOrigen: '',
      tiempoCoccion: '',
      dificultad: '',
      usuario: ''
    });
  });
  
  it('should cancel adding recipe and reset form', () => {
    component.mostrarFormulario = true;
    component.recetaForm = { ...mockRecetas[0] };
  
    component.cancelarAgregarReceta();
  
    expect(component.mostrarFormulario).toBeFalse();
    expect(component.recetaForm).toEqual({
      nombre: '',
      ingredientes: '',
      instrucciones: '',
      tipoCocina: '',
      paisOrigen: '',
      tiempoCoccion: '',
      dificultad: '',
      usuario: ''
    });
  });  

  it('should handle errors when fetching recipes', () => {
    recetasService.obtenerRecetas.and.returnValue(throwError(() => new Error('Debe iniciar sesión')));
    spyOn(component, 'cerrarSesion');

    component.obtenerRecetas();

    expect(component.cerrarSesion).toHaveBeenCalled();
  });

  it('should update a recipe when editing', () => {
    recetasService.actualizarReceta.and.returnValue(of(mockRecetas[0]));

    component.recetaSeleccionada = mockRecetas[0];
    component.recetaForm = { ...mockRecetas[0] };
    component.agregarReceta();

    expect(recetasService.actualizarReceta).toHaveBeenCalledWith(
      mockRecetas[0].id!,
      mockRecetas[0]
    );
  });

  it('should delete a recipe', () => {
    recetasService.eliminarReceta.and.returnValue(of());

    component.eliminarReceta(mockRecetas[0].id!);

    expect(recetasService.eliminarReceta).toHaveBeenCalledWith(mockRecetas[0].id!);
  });

  it('should edit a recipe', () => {
    const recetaAEditar = { ...mockRecetas[0] };
  
    component.editarReceta(recetaAEditar);
  
    expect(component.recetaSeleccionada).toEqual(recetaAEditar); // Verifica que la receta seleccionada se actualice
    expect(component.recetaForm).toEqual(recetaAEditar); // Verifica que el formulario se actualice con los datos de la receta
    expect(component.mostrarFormulario).toBeTrue(); // Verifica que el formulario esté visible
  });

  it('should clear authToken and navigate to login on cerrarSesion', () => {
    spyOn(localStorage, 'removeItem');

    component.cerrarSesion();

    expect(localStorage.removeItem).toHaveBeenCalledWith('authToken');
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should share a recipe on Twitter', () => {
    spyOn(window, 'prompt').and.returnValue('1'); // Simula selección de Twitter
    spyOn(window, 'open'); // Mockea window.open

    const recetaMock = { id: 1, nombre: 'Receta 1' } as Receta;
    component.compartirReceta(recetaMock);

    const expectedUrl = `https://twitter.com/intent/tweet?text=Mira%20esta%20incre%C3%ADble%20receta%20de%20Receta%201%20que%20encontr%C3%A9%3A&url=http%3A%2F%2Flocalhost%2Frecetas%2F1`;

    expect(window.open).toHaveBeenCalledWith(expectedUrl, '_blank');
  });

  it('should share a recipe on Facebook', () => {
    spyOn(window, 'prompt').and.returnValue('2'); // Simula selección de Facebook
    spyOn(window, 'open'); // Mockea window.open

    const recetaMock = { id: 1, nombre: 'Receta 1' } as Receta;
    component.compartirReceta(recetaMock);

    const expectedUrl = `https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Flocalhost%2Frecetas%2F1`;

    expect(window.open).toHaveBeenCalledWith(expectedUrl, '_blank');
  });

  it('should share a recipe on WhatsApp', () => {
    spyOn(window, 'prompt').and.returnValue('3'); // Simula selección de WhatsApp
    spyOn(window, 'open'); // Mockea window.open

    const recetaMock = { id: 1, nombre: 'Receta 1' } as Receta;
    component.compartirReceta(recetaMock);

    const expectedUrl = `https://wa.me/?text=Mira%20esta%20incre%C3%ADble%20receta%20de%20Receta%201%20que%20encontr%C3%A9%3A%20http%3A%2F%2Flocalhost%2Frecetas%2F1`;

    expect(window.open).toHaveBeenCalledWith(expectedUrl, '_blank');
  });

  it('should alert user when an invalid option is selected', () => {
    spyOn(window, 'prompt').and.returnValue('5'); // Simula selección inválida
    spyOn(window, 'alert'); // Mockea window.alert

    const recetaMock = { id: 1, nombre: 'Receta 1' } as Receta;
    component.compartirReceta(recetaMock);

    expect(window.alert).toHaveBeenCalledWith('Opción no válida');
  });

  it('should alert user when an invalid option is selected', () => {
    spyOn(window, 'prompt').and.returnValue('5');
    spyOn(window, 'alert');

    const recetaMock = { id: 1, nombre: 'Receta 1' } as Receta;
    component.compartirReceta(recetaMock);

    expect(window.alert).toHaveBeenCalledWith('Opción no válida');
  });
  
  it('should call recetasService.actualizarReceta when updating a recipe', () => {
    // Espías para métodos del componente
    const obtenerRecetasSpy = spyOn(component, 'obtenerRecetas').and.callThrough();
    const resetFormularioSpy = spyOn(component, 'resetFormulario').and.callThrough();
  
    // Mock del servicio
    recetasService.actualizarReceta.and.returnValue(of(mockRecetas[0]));
  
    // Configura los datos necesarios
    component.recetaSeleccionada = { ...mockRecetas[0] };
    component.recetaForm = { ...mockRecetas[0] };
  
    // Ejecuta el método
    component.actualizarReceta();
  
    // Verifica que se llamen los métodos esperados
    expect(recetasService.actualizarReceta).toHaveBeenCalledWith(mockRecetas[0].id!, mockRecetas[0]);
    expect(obtenerRecetasSpy).toHaveBeenCalled();
    expect(resetFormularioSpy).toHaveBeenCalled();
  });  
  
  it('should call recetasService.eliminarReceta when deleting a recipe', () => {
    // Espías para métodos del componente
    const obtenerRecetasSpy = spyOn(component, 'obtenerRecetas').and.callThrough();
  
    // Mock del servicio
    recetasService.eliminarReceta.and.returnValue(of(undefined));
  
    // Ejecuta el método
    component.eliminarReceta(mockRecetas[0].id!);
  
    // Verifica que se llamen los métodos esperados
    expect(recetasService.eliminarReceta).toHaveBeenCalledWith(mockRecetas[0].id!);
    expect(obtenerRecetasSpy).toHaveBeenCalled();
  });
  
  
  it('should handle error when deleting a recipe', () => {
    spyOn(component, 'cerrarSesion').and.callThrough();
  
    const errorResponse = { message: 'Debe iniciar sesión' };
    recetasService.eliminarReceta.and.returnValue(throwError(() => errorResponse)); // Simula un error
  
    component.eliminarReceta(mockRecetas[0].id!);
  
    expect(recetasService.eliminarReceta).toHaveBeenCalledWith(mockRecetas[0].id!);
    expect(component.cerrarSesion).toHaveBeenCalled(); // Verifica que se llame a cerrarSesion en caso de error
  });  
  
});
