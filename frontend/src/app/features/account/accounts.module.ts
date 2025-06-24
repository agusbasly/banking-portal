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

@NgModule({
  declarations: [
    AccountsComponent,
    AccountFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AccountsRoutingModule,
    MatCardModule,
    MatTableModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class AccountsModule {}
