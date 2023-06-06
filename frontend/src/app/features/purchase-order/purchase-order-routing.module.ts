import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from '../../shared/layout/layout.component';
import { AddPurchaseOrderPageComponent } from './add-purchase-order-page/add-purchase-order-page.component';
import { PurchaseOrderPageComponent } from './purchase-order-page/purchase-order-page.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', component: PurchaseOrderPageComponent },
      { path: 'create', component: AddPurchaseOrderPageComponent },
      { path: 'edit', component: AddPurchaseOrderPageComponent },
      { path: 'approve', component: AddPurchaseOrderPageComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PurchaseOrderRoutingModule { }
