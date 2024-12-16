import { TestBed } from '@angular/core/testing';
import { UsuarioService } from './usuario.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('UsuarioService', () => {
  let service: UsuarioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsuarioService]
    });

    service = TestBed.inject(UsuarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('debería obtener la lista de usuarios', () => {
    const mockUsuarios = [{ id: 1, username: 'admin', role: 'ADMIN' }];

    service.obtenerUsuarios().subscribe((usuarios) => {
      expect(usuarios.length).toBe(1);
      expect(usuarios[0].username).toBe('admin');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/usuarios');
    expect(req.request.method).toBe('GET');
    req.flush(mockUsuarios);
  });
});
