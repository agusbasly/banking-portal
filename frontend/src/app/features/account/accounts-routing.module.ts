import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AccountsComponent } from './list/account.component';
import { AccountFormComponent } from './form/account-form.component';

const routes: Routes = [
  { path: '',   component: AccountsComponent },
  { path: 'new', component: AccountFormComponent }
];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class AccountsRoutingModule {}
