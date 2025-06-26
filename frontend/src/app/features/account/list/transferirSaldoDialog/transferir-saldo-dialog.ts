import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AccountService } from 'src/app/services/account.service';
import { Account } from 'src/app/models/account.model';

@Component({
  selector: 'app-transferir-saldo-dialog',
  templateUrl: './transferir-saldo-dialog.html'
})
export class TransferirSaldoDialogComponent implements OnInit {
  cuentas: Account[] = [];
  transferencia = {
    origenId: this.data.origen.id,
    destinoId: '',
    monto: 0
  };

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { origen: Account },
    private dialogRef: MatDialogRef<TransferirSaldoDialogComponent>,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountService.getAccounts().subscribe(cuentas => {
      this.cuentas = cuentas.filter(c => c.id !== this.data.origen.id);
    });
  }

  transferir(): void {
    this.dialogRef.close(this.transferencia);
  }

  cancelar(): void {
    this.dialogRef.close();
  }
}
