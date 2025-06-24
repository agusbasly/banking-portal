import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountsComponent implements OnInit {
  accounts: Account[] = [];
  displayedColumns: string[] = ['id', 'numeroCuenta', 'nombreDueno', 'email', 'balance']; // ✅ Agregalo

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAccounts().subscribe(data => {
      console.log("Cuentas cargadas:", data); // 👀 Verificá en la consola
      this.accounts = data;
    });
  }
}
