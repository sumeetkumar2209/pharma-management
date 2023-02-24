import { Injectable } from '@angular/core';
import { SupplierInterface } from './supplier.interface';

@Injectable()
export class SupplierService {
    selectedSupplier!:SupplierInterface;
    constructor() { }
    
}