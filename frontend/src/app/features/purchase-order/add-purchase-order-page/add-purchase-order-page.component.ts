import { Component, ViewChild } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PurchaseOrderInterface, PurchaseOrderLineItemInterface } from '../purchase-order.interface';
import { PurchaseOrderService } from '../purchase-order.service';
import { Table } from 'primeng/table';
import { ProductInterface, ProductStatus, WorkflowStatus } from '../../product/product.interface';


@Component({
  selector: 'app-add-purchase-order-page',
  templateUrl: './add-purchase-order-page.component.html',
  styleUrls: ['./add-purchase-order-page.component.css'],
  styles:[`
  :host ::ng-deep .p-cell-editing {
      padding-top: 0 !important;
      padding-bottom: 0 !important;
  }
`]
})
export class AddPurchaseOrderPageComponent {
  @ViewChild(Table, { read: Table }) pTable!: Table;
  addPurchaseOrderForm: FormGroup;
  purchaseOrder: any;
  countryList: String[] = ['IN', 'UK', 'USA'];
  currencyList: String[] = ['INR', 'USD', 'GBP'];
  statusList: String[] = ['Active', 'Inactive'];
  qualifyList: String[] = ['QUALIFIED', 'NOT-QUALIFIED'];
  approverList: String[] = ['Vijay', 'Prakash'];
  approvalStatusOption: String[] = ['Approved', 'Rejected'];
  editForm: boolean = false;
  approverForm: boolean = false;
  _purchaseOrder!: PurchaseOrderInterface;

  ponumber = 101;
  products!: PurchaseOrderLineItemInterface[];

  statuses!: any[];

  clonedProducts: { [s: string]: PurchaseOrderLineItemInterface; } = {};

  productOptions: any[]=[
    {
      label: 'P101',
      value:'P101'
      
    },
    {
      label: 'P102',
      value: 'P102'
     
    },
  ];

  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger, private notificationService: NotificationService,
    private router: Router,
    private service: PurchaseOrderService) {
    if (this.router.url.includes('edit')) {
      this.editForm = true;
      this.approverForm = false;

    } else if (this.router.url.includes('approve')) {
      this.editForm = false;
      this.approverForm = true;
    }
    this.addPurchaseOrderForm = this.fb.group({
    

    });

    if (this.editForm) {
      this._purchaseOrder = this.service.selectedPurchaseOrder;
      this.populateFormFields();
    } else if (this.approverForm) {



    }

    console.log(this.router.url);
  }
  ngOnInit() {
    console.log(this.editForm);
    this.titleService.setTitle('Reiphy Pharma - Add PurchaseOrder');
    this.logger.log('Add PurchaseOrder loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Fill in the form to Add PurchaseOrder!');
    });
    this.products = [{
      id:'1',
      productId:'PROD001',
      licenseNumber: '12345',
      productName: 'Parasafe',
      dosageForm: 'Tablets',
      strength:'500mg',
      packType: 'Strip',
      packSize: 10,
      supplierName: 'Ab & Co',
      purchaseOrderQuantity: '10',
      pricePerPack: 50,
      totalProductAmount: '500'
    }
    ];
  }
  populateFormFields() {
 
  }
  onSubmit(post: any) {
    this.purchaseOrder = post;
  }

  get apControls(): { [key: string]: AbstractControl } {
    return this.addPurchaseOrderForm.controls;
  }

  getError(controlName: string) {
    return this.apControls[controlName].hasError('required') ? 'Field is required' :
      this.apControls[controlName].hasError('pattern') ? 'Not a valid pattern' :
        '';
  }

  save() { }
  saveAsDraft(){}

  onRowEditInit(product: PurchaseOrderLineItemInterface) {
    this.clonedProducts[product.productId] = {...product};
}

onRowEditSave(product: PurchaseOrderLineItemInterface) {
    // if (product.price > 0) {
    //     delete this.clonedProducts[product.productId];
    //     this.messageService.add({severity:'success', summary: 'Success', detail:'Product is updated'});
    // }
    // else {
    //     this.messageService.add({severity:'error', summary: 'Error', detail:'Invalid Price'});
    // }
}

onRowEditCancel(product: PurchaseOrderLineItemInterface, index: number) {
    this.products[index] = this.clonedProducts[product.productId];
    delete this.clonedProducts[product.productId];
}

onAddNewRow() {
  const nextId = this.products.length>0?parseInt(this.products[this.products.length-1].id,10)+1:1;
  const newP:PurchaseOrderLineItemInterface = {
    id: nextId.toString(),
    productId:'',
      licenseNumber: '',
      productName: '',
      dosageForm: '',
      strength:'',
      packType: '',
      packSize: 0,
      supplierName: '',
      purchaseOrderQuantity: '',
      pricePerPack: 0,
      totalProductAmount: ''
  };
  this.products.unshift(newP);
  //Caution: guard again dataKey here
  this.pTable.editingRowKeys[newP[this.pTable.dataKey as keyof PurchaseOrderLineItemInterface]] = true;
  this.onRowEditInit(newP);
}


}
