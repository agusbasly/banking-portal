import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AccountsRoutingModule } from './accounts-routing.module';
import { AccountsComponent } from './list/account.component';
import { AccountFormComponent } from './form/account-form.component';


// Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';      
import { MatDialogModule } from '@angular/material/dialog';
import { MovimientosDialogComponent } from './list/movimientos-dialog/movimientos-dialog.component';
import { EditarSaldoDialogComponent } from './list/editarSaldoDialogComponent/editar-saldo-dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TransferirSaldoDialogComponent } from './list/transferirSaldoDialog/transferir-saldo-dialog';
import { MatSelectModule } from '@angular/material/select';



@NgModule({
  declarations: [
    AccountsComponent,
    AccountFormComponent,
    MovimientosDialogComponent,
    EditarSaldoDialogComponent,
    TransferirSaldoDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AccountsRoutingModule,
    MatCardModule,
    MatTableModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule
  ]
})
export class AccountsModule {}
