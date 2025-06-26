import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../models/account.model';
import { AccountCreateDTO } from '../models/account-create.dto';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/accounts'; 

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUrl);
  }

  crearCuenta(cuenta: AccountCreateDTO): Observable<Account> {
    return this.http.post<Account>(this.apiUrl, cuenta);
  }

  getMovimientosByCuentaId(cuenta: string): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/movimientos/${cuenta}`);
  }

  deleteAccount(id: string): Observable<void> {
  return this.http.delete<void>(`http://localhost:8080/accounts/${id}`);
  }

  updateBalance(id: string, nuevoBalance: number): Observable<Account> {
  return this.http.put<Account>(`http://localhost:8080/accounts/${id}?saldo=${nuevoBalance}`, null);
  }

  transferirSaldo(dto: { origenId: string, destinoId: string, monto: number }): Observable<void> {
  return this.http.post<void>('http://localhost:8080/transferencias', dto);
}

}
