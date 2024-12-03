import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RecetasService } from './recetas.service';
import { Receta } from '../models/receta.model';

describe('RecetasService', () => {
  let service: RecetasService;
  let httpMock: HttpTestingController;

  const mockRecetas: Receta[] = [
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

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RecetasService],
    });

    service = TestBed.inject(RecetasService);
    httpMock = TestBed.inject(HttpTestingController);

    // Simulación global de localStorage.getItem
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'authToken') {
        return 'mockAuthToken'; // Devuelve un token simulado
      }
      return null;
    });
  });

  afterEach(() => {
    httpMock.verify(); // Verifica que no haya solicitudes pendientes
  });

  it('should throw an error if no token is available', () => {
    (localStorage.getItem as jasmine.Spy).and.returnValue(null);
  
    expect(() => service.obtenerRecetas()).toThrowError('Debe iniciar sesión');
  });

  it('should retrieve recetas from API via GET', () => {
    service.obtenerRecetas().subscribe((recetas) => {
      expect(recetas).toEqual(mockRecetas);
    });

    const req = httpMock.expectOne('http://localhost:8082/api/recetas');
    expect(req.request.method).toBe('GET');
    req.flush(mockRecetas);
  });

  it('should add a new receta via POST', () => {
    const newReceta: Receta = {
      id: 2,
      nombre: 'Receta 2',
      ingredientes: 'Ingrediente 2',
      instrucciones: 'Instrucción 2',
      tipoCocina: 'Mexicana',
      paisOrigen: 'México',
      tiempoCoccion: '45 min',
      dificultad: 'Media',
      usuario: 'User2',
    };

    service.agregarReceta(newReceta).subscribe((receta) => {
      expect(receta).toEqual(newReceta);
    });

    const req = httpMock.expectOne('http://localhost:8082/api/recetas');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newReceta);
    req.flush(newReceta);
  });

  it('should handle an error when adding a receta via POST', () => {
    const newReceta: Receta = {
      id: 2,
      nombre: 'Receta 2',
      ingredientes: 'Ingrediente 2',
      instrucciones: 'Instrucción 2',
      tipoCocina: 'Mexicana',
      paisOrigen: 'México',
      tiempoCoccion: '45 min',
      dificultad: 'Media',
      usuario: 'User2',
    };
  
    service.agregarReceta(newReceta).subscribe(
      () => fail('Expected an error, not a receta'),
      (error) => {
        expect(error.message).toContain(
          'Http failure response for http://localhost:8082/api/recetas: 500 Internal Server Error'
        );
      }
    );
  
    const req = httpMock.expectOne('http://localhost:8082/api/recetas');
    expect(req.request.method).toBe('POST');
    req.flush({ message: 'Error al agregar receta' }, { status: 500, statusText: 'Internal Server Error' });
  });
  
  it('should handle an error when updating a receta via PUT', () => {
    const updatedReceta: Receta = { ...mockRecetas[0], nombre: 'Receta Actualizada' };
  
    service.actualizarReceta(updatedReceta.id!, updatedReceta).subscribe(
      () => fail('Expected an error, not a receta'),
      (error) => {
        expect(error.message).toContain(
          'Http failure response for http://localhost:8082/api/recetas/1: 500 Internal Server Error'
        );
      }
    );
  
    const req = httpMock.expectOne(`http://localhost:8082/api/recetas/${updatedReceta.id}`);
    expect(req.request.method).toBe('PUT');
    req.flush({ message: 'Error al actualizar receta' }, { status: 500, statusText: 'Internal Server Error' });
  });
  
  it('should handle an error when deleting a receta via DELETE', () => {
    const recetaId = 1;
  
    service.eliminarReceta(recetaId).subscribe(
      () => fail('Expected an error, not void'),
      (error) => {
        expect(error.message).toContain(
          'Http failure response for http://localhost:8082/api/recetas/1: 500 Internal Server Error'
        );
      }
    );
  
    const req = httpMock.expectOne(`http://localhost:8082/api/recetas/${recetaId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({ message: 'Error al eliminar receta' }, { status: 500, statusText: 'Internal Server Error' });
  });
  

  it('should handle an error when retrieving recetas', () => {
    service.obtenerRecetas().subscribe(
      () => fail('Expected an error, not recetas'),
      (error) => {
        expect(error.message).toContain('Debe iniciar sesión');
      }
    );

    const req = httpMock.expectOne('http://localhost:8082/api/recetas');
    expect(req.request.method).toBe('GET');
    req.flush({ message: 'Debe iniciar sesión' }, { status: 401, statusText: 'Unauthorized' });
  });

});
