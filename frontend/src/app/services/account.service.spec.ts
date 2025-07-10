import { TestBed } from '@angular/core/testing';
import { AccountService } from './account.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Account } from '../models/account.model';
import { AccountCreateDTO } from '../models/account-create.dto';


describe('AccountService', () => {
  let service: AccountService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AccountService]
    });

    service = TestBed.inject(AccountService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('debería obtener la lista de cuentas (GET)', () => {
    const dummyAccounts: Account[] = [
      { id: '1', balance: 100, numeroCuenta: '123456', nombreDueno: 'Juan Perez', email: 'juan@example.com' },
      { id: '2', balance: 200, numeroCuenta: '654321', nombreDueno: 'Maria Gomez', email: 'maria@example.com' }
    ];

    service.getAccounts().subscribe(accounts => {
      expect(accounts.length).toBe(2);
      expect(accounts).toEqual(dummyAccounts);
    });

    const req = httpMock.expectOne('/accounts');
    expect(req.request.method).toBe('GET');
    req.flush(dummyAccounts);
  });

  it('debería crear una cuenta (POST)', () => {
  const newAccount: AccountCreateDTO = {
    nombreDueno: 'Pedro Soto',
    email: 'pedro@example.com',
    numeroCuenta: '789456',
    balance: 300
  };

  service.crearCuenta(newAccount).subscribe(response => {
    expect(response).toEqual({ id: '3', ...newAccount }); // asumimos que el backend agrega el id
  });

  const req = httpMock.expectOne('/accounts');
  expect(req.request.method).toBe('POST');
  expect(req.request.body).toEqual(newAccount); // se envía correctamente
  req.flush({ id: '3', ...newAccount }); // lo que simula devolver el backend
});
it('deleteAccount debe llamar DELETE /accounts/:id', () => {
    service.deleteAccount('42').subscribe(() => {/* ok */});
    const req = httpMock.expectOne('/accounts/42');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('updateBalance debe llamar PUT /accounts/:id?saldo=', () => {
    service.updateBalance('42', 500).subscribe(res => expect(res).toBeTruthy());
    const req = httpMock.expectOne('/accounts/42?saldo=500');
    expect(req.request.method).toBe('PUT');
    req.flush({ id: '42', balance: 500 });
  });

  it('getMovimientosByCuentaId debe llamar GET /movimientos/:id', () => {
    const mock = [{ id: 'm1' }];
    service.getMovimientosByCuentaId('42').subscribe(arr => expect(arr).toEqual(mock));
    const req = httpMock.expectOne('/movimientos/42');
    expect(req.request.method).toBe('GET');
    req.flush(mock);
  });

  it('transferirSaldo debe llamar POST /transferencias', () => {
    const dto = { origenId: '1', destinoId: '2', monto: 150 };
    service.transferirSaldo(dto).subscribe(() => {});
    const req = httpMock.expectOne('/transferencias');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dto);
    req.flush(null);
  });

  it('debe propagar error de GET', () => {
    service.getAccounts().subscribe({
      next: () => fail('no debería emitirse'),
      error: err => expect(err.status).toBe(500)
    });
    const req = httpMock.expectOne('/accounts');
    req.flush('error', { status: 500, statusText: 'Server Error' });
  });
});
