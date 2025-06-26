import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-movimientos-dialog',
  templateUrl: './movimientos-dialog.component.html',
  styleUrls: ['./movimientos-dialog.component.scss']
})
export class MovimientosDialogComponent {
  detalleSeleccionado: any | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  verDetalle(movimiento: any): void {
    this.detalleSeleccionado = movimiento;
  }

  cerrarDetalle(): void {
    this.detalleSeleccionado = null;
  }
}
