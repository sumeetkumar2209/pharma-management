import { Injectable } from '@angular/core';
import { PurchaseOrderInterface } from './purchase-order.interface';

@Injectable()
export class PurchaseOrderService {
    selectedPurchaseOrder!:PurchaseOrderInterface;
    constructor() { }
    
}