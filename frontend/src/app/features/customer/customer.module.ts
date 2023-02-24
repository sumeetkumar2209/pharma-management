import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { CustomerPageComponent } from './customer-page/customer-page.component';
import { CustomMaterialModule } from 'src/app/custom-material/custom-material.module';
import { AddCustomerPageComponent } from './add-customer-page/add-customer-page.component';
import { CustomerService } from './customer.service';

@NgModule({
  declarations: [CustomerPageComponent,AddCustomerPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    CustomerRoutingModule,
    CustomMaterialModule
  ],
  providers:[CustomerService]
})
export class CustomerModule { }
