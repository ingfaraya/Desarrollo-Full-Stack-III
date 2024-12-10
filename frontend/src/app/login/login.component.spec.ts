import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComponent, HttpClientTestingModule, RouterTestingModule]
    }).compileComponents();

    const fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
  });

  it('debería crearse correctamente', () => {
    expect(component).toBeTruthy();
  });

  it('debería realizar login exitosamente y redirigir a /recetas', () => {
    spyOn(localStorage, 'setItem');
    spyOn(component['http'], 'post').and.returnValue(of({ token: 'test-token' }));
    spyOn(component['router'], 'navigate');

    component.username = 'testuser';
    component.password = 'testpass';
    component.login();

    expect(localStorage.setItem).toHaveBeenCalledWith('authToken', 'test-token');
    expect(component['router'].navigate).toHaveBeenCalledWith(['/recetas']);
  });

  it('debería manejar errores de login correctamente', () => {
    spyOn(window, 'alert');
    spyOn(component['http'], 'post').and.returnValue(throwError({ status: 401 }));

    component.login();

    expect(window.alert).toHaveBeenCalledWith('Nombre de usuario o contraseña incorrectos');
  });
});
