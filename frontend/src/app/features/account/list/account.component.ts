import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { AccountService } from '../../../services/account.service';
import { Account } from '../../../models/account.model';

import { MovimientosDialogComponent } from '../list/movimientos-dialog/movimientos-dialog.component';
import { EditarSaldoDialogComponent } from './editarSaldoDialogComponent/editar-saldo-dialog';
import { TransferirSaldoDialogComponent } from './transferirSaldoDialog/transferir-saldo-dialog';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountsComponent implements OnInit {
  accounts: Account[] = [];
  displayedColumns: string[] = [
    'id',
    'numeroCuenta',
    'nombreDueno',
    'email',
    'balance',
    'acciones'
  ];

  constructor(
    private accountService: AccountService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.accountService.getAccounts().subscribe(data => {
      console.log('Cuentas cargadas:', data);
      this.accounts = data;
    });
  }

  verMovimientos(account: Account): void {
    this.accountService.getMovimientosByCuentaId(account.id).subscribe({
      next: movimientos => {
        this.dialog.open(MovimientosDialogComponent, {
          width: '600px',
          data: { movimientos }
        });
      },
      error: err => {
        console.error('Error al obtener movimientos', err);
      }
    });
  }

  confirmarBorrado(account: Account): void {
    const confirmado = confirm(
      `¿Estás seguro que querés eliminar la cuenta N° ${account.numeroCuenta}?`
    );

    if (confirmado) {
      this.accountService.deleteAccount(account.id).subscribe({
        next: () => {
          this.accounts = this.accounts.filter(a => a.id !== account.id);
        },
        error: err => {
          console.error('Error al eliminar la cuenta:', err);
          alert('No se pudo eliminar la cuenta. Intentalo de nuevo.');
        }
      });
    }
  }

  abrirDialogoEditar(account: Account): void {
    const dialogRef = this.dialog.open(EditarSaldoDialogComponent, {
      width: '300px',
      data: { ...account }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const { id, balance } = result;
        this.accountService.updateBalance(id, balance).subscribe(() => {
          const cuenta = this.accounts.find(a => a.id === id);
          if (cuenta) cuenta.balance = balance;
        });
      }
    });
  }

  abrirDialogoTransferencia(origen: Account): void {
    const dialogRef = this.dialog.open(TransferirSaldoDialogComponent, {
      width: '400px',
      data: { origen }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.accountService.transferirSaldo(result).subscribe({
          next: () => {
            alert('Transferencia realizada correctamente');
            this.ngOnInit();
          },
          error: err => {
            console.error('Error en la transferencia:', err);
            alert('Error al realizar la transferencia');
          }
        });
      }
    });
  }
}
