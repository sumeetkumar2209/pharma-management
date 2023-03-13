import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from '../../shared/layout/layout.component';
import { AddCustomerPageComponent } from './add-customer-page/add-customer-page.component';
import { CustomerPageComponent } from './customer-page/customer-page.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', component: CustomerPageComponent },
      { path: 'add', component: AddCustomerPageComponent },
      { path: 'edit', component: AddCustomerPageComponent },
      { path: 'approve', component: AddCustomerPageComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
