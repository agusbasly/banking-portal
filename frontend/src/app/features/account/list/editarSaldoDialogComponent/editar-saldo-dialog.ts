import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Account } from '../../../../models/account.model';

@Component({
  selector: 'app-editar-saldo-dialog',
  templateUrl: './editar-saldo-dialog.html'
})
export class EditarSaldoDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Account,
    private dialogRef: MatDialogRef<EditarSaldoDialogComponent>
  ) {}

  guardar() {
    this.dialogRef.close(this.data);
  }

  cancelar() {
    this.dialogRef.close();
  }
}
