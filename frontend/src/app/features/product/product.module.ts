import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../../shared/shared.module';
import { AddProductPageComponent } from './add-product-page/add-product-page.component';
import { ProductRoutingModule } from './product-routing.module';
import { CustomMaterialModule } from 'src/app/custom-material/custom-material.module';
import { ProductPageComponent } from './product-page/product-page.component';

@NgModule({
  declarations: [AddProductPageComponent,ProductPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    ProductRoutingModule,
    CustomMaterialModule
  ]
})
export class ProductModule { }
