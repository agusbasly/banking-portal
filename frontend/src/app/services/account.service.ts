import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../models/account.model';
import { AccountCreateDTO } from '../models/account-create.dto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = `${environment.apiBaseUrl}/accounts`;
  private movimientosUrl = `${environment.apiBaseUrl}/movimientos`;
  private transferenciasUrl = `${environment.apiBaseUrl}/transferencias`;

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUrl);
  }

  crearCuenta(cuenta: AccountCreateDTO): Observable<Account> {
    return this.http.post<Account>(this.apiUrl, cuenta);
  }

  getMovimientosByCuentaId(cuenta: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.movimientosUrl}/${cuenta}`);
  }

  deleteAccount(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateBalance(id: string, nuevoBalance: number): Observable<Account> {
    return this.http.put<Account>(`${this.apiUrl}/${id}?saldo=${nuevoBalance}`, null);
  }

  transferirSaldo(dto: { origenId: string, destinoId: string, monto: number }): Observable<void> {
    return this.http.post<void>(this.transferenciasUrl, dto);
  }
}
