import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from '../../shared/layout/layout.component';
import { AddSupplierPageComponent } from './add-supplier-page/add-supplier-page.component';
import { PendingSupplierPageComponent } from './pending-supplier-page/pending-supplier-page.component';
import { SupplierPageComponent } from './supplier-page/supplier-page.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', component: SupplierPageComponent },
      { path: 'add', component: AddSupplierPageComponent },
      { path: 'edit', component: AddSupplierPageComponent },
      { path: 'approve', component: AddSupplierPageComponent },
      { path: 'pending', component: PendingSupplierPageComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupplierRoutingModule { }
