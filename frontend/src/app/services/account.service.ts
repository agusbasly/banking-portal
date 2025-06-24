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

}
