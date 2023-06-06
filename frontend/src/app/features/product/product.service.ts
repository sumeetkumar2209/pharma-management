import { Injectable } from '@angular/core';
import { ProductInterface } from './product.interface';

@Injectable()
export class ProductService {
    selectedProduct!:ProductInterface;
    constructor() { }
    
}