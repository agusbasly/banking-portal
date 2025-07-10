import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { AccountsComponent } from './account.component';
import { AccountService } from '../../../services/account.service';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { By } from '@angular/platform-browser';

describe('AccountsComponent', () => {
  let component: AccountsComponent;
  let fixture: ComponentFixture<AccountsComponent>;
  let accountServiceSpy: jasmine.SpyObj<AccountService>;
  let dialogSpy: jasmine.SpyObj<MatDialog>;

  beforeEach(async () => {
    accountServiceSpy = jasmine.createSpyObj('AccountService', ['getAccounts', 'getMovimientosByCuentaId']);
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    await TestBed.configureTestingModule({
      declarations: [AccountsComponent],
      imports: [HttpClientTestingModule, MatDialogModule, MatTableModule],
      providers: [
        { provide: AccountService, useValue: accountServiceSpy },
        { provide: MatDialog, useValue: dialogSpy }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(AccountsComponent);
    component = fixture.componentInstance;
  });

  it('debería crear el componente', () => {
    expect(component).toBeTruthy();
  });

  it('debería cargar las cuentas en ngOnInit', fakeAsync(() => {
    const mockAccounts = [
      { id: '1', nombre: 'Cuenta A', balance: 100, numeroCuenta: '123456', nombreDueno: 'Juan Perez', email: 'juan@example.com' }
    ];
    accountServiceSpy.getAccounts.and.returnValue(of(mockAccounts));

    component.ngOnInit();
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    expect(component.accounts).toEqual(mockAccounts);
    expect(accountServiceSpy.getAccounts).toHaveBeenCalled();
  }));

  it('debería mostrar 2 filas en la tabla', fakeAsync(() => {
    const mockAccounts = [
      { id: '1', nombre: 'Cuenta A', balance: 100, numeroCuenta: '123456', nombreDueno: 'Juan Perez', email: 'juan@example.com' },
      { id: '2', nombre: 'Cuenta B', balance: 200, numeroCuenta: '654321', nombreDueno: 'Maria Gomez', email: 'maria@example.com' }
    ];
    accountServiceSpy.getAccounts.and.returnValue(of(mockAccounts));

    component.ngOnInit();
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const rows = fixture.nativeElement.querySelectorAll('tr.mat-row');
    expect(rows.length).toBe(2);
  }));

  it('debería manejar lista vacía sin filas', fakeAsync(() => {
    accountServiceSpy.getAccounts.and.returnValue(of([]));

    component.ngOnInit();
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const rows = fixture.debugElement.queryAll(By.css('tr.mat-row'));
    expect(rows.length).toBe(0);
  }));

  it('al hacer click en ver movimientos abre diálogo con movimientos', fakeAsync(() => {
    const cuenta = { id: '1', nombre: 'Cuenta A', balance: 100, numeroCuenta: '123456', nombreDueno: 'Juan Perez', email: 'juan@example.com' };
    const movimientos = [{ id: 'm1' }];
    accountServiceSpy.getAccounts.and.returnValue(of([cuenta]));
    accountServiceSpy.getMovimientosByCuentaId.and.returnValue(of(movimientos));

    component.ngOnInit();
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const btns = fixture.debugElement.queryAll(By.css('button'));
    btns[0].triggerEventHandler('click', null);

    expect(dialogSpy.open).toHaveBeenCalledWith(
      jasmine.any(Function),
      jasmine.objectContaining({ width: '600px', data: { movimientos } })
    );
  }));
});
