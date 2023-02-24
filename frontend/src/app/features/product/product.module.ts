import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { ProductPageComponent } from './product-page/product-page.component';
import { CustomMaterialModule } from 'src/app/custom-material/custom-material.module';
import { AddProductPageComponent } from './add-product-page/add-product-page.component';
import { ProductService } from './product.service';

@NgModule({
  declarations: [ProductPageComponent,AddProductPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    ProductRoutingModule,
    CustomMaterialModule
  ],
  providers:[ProductService]
})
export class ProductModule { }
