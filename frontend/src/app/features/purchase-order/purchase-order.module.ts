import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PurchaseOrderRoutingModule } from './purchase-order-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { PurchaseOrderPageComponent } from './purchase-order-page/purchase-order-page.component';
import { CustomMaterialModule } from 'src/app/custom-material/custom-material.module';
import { AddPurchaseOrderPageComponent } from './add-purchase-order-page/add-purchase-order-page.component';
import { PurchaseOrderService } from './purchase-order.service';
import {InputTextModule} from 'primeng/inputtext';
import {DropdownModule} from 'primeng/dropdown';

@NgModule({
  declarations: [PurchaseOrderPageComponent,AddPurchaseOrderPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    PurchaseOrderRoutingModule,
    CustomMaterialModule,
    InputTextModule,
    DropdownModule
  ],
  providers:[PurchaseOrderService]
})
export class PurchaseOrderModule { }
