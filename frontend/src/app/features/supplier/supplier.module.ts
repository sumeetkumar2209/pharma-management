import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupplierRoutingModule } from './supplier-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { SupplierPageComponent } from './supplier-page/supplier-page.component';
import { CustomMaterialModule } from 'src/app/custom-material/custom-material.module';
import { AddSupplierPageComponent } from './add-supplier-page/add-supplier-page.component';
import { SupplierService } from './supplier.service';
import { PendingSupplierPageComponent } from './pending-supplier-page/pending-supplier-page.component';

@NgModule({
  declarations: [SupplierPageComponent,AddSupplierPageComponent,PendingSupplierPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    SupplierRoutingModule,
    CustomMaterialModule
  ],
  providers:[SupplierService]
})
export class SupplierModule { }
