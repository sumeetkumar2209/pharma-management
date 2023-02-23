import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupplierRoutingModule } from './supplier-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { SupplierPageComponent } from './supplier-page/supplier-page.component';
import { CustomMaterialModule } from 'src/app/custom-material/custom-material.module';
import { AddSupplierPageComponent } from './add-supplier-page/add-supplier-page.component';

@NgModule({
  declarations: [SupplierPageComponent,AddSupplierPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    SupplierRoutingModule,
    CustomMaterialModule
  ]
})
export class SupplierModule { }
