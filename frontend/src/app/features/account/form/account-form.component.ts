import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { AccountCreateDTO } from 'src/app/models/account-create.dto';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.scss']
})

export class AccountFormComponent {
  cuenta: AccountCreateDTO = {
    nombreDueno: '',
    email: '',
    numeroCuenta: '',
    balance: 0
  };

  constructor(
    private accountService: AccountService,
    private router: Router
  ) {}

  guardar(): void {
    this.accountService.crearCuenta(this.cuenta).subscribe({
      next: () => {
        console.log('Cuenta creada exitosamente');
        this.router.navigate(['/accounts']);
      },
      error: err => {
        console.error('Error al crear cuenta', err);
      }
    });
  }
}
