import { Injectable } from '@angular/core';
import { CustomerInterface } from './customer.interface';

@Injectable()
export class CustomerService {
    selectedCustomer!:CustomerInterface;
    constructor() { }
    
}