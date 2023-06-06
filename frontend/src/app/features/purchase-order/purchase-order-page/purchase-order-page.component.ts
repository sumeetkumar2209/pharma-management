import { Component, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PurchaseOrderInterface, PurchaseOrderStatus, WorkflowStatus } from 'src/app/features/purchase-order/purchase-order.interface';
import { Router } from '@angular/router';
import { PurchaseOrderService } from '../purchase-order.service';



@Component({
  selector: 'app-purchase-order-page',
  templateUrl: './purchase-order-page.component.html',
  styleUrls: ['./purchase-order-page.component.css'],

})
export class PurchaseOrderPageComponent {
  filterPurchaseOrderForm!: FormGroup;

  animal!: string;
  name!: string;
  purchaseOrders: PurchaseOrderInterface[] = [
    {
      purchaseOrderNumber: 'P012348',
      supplierName: 'Ab & Co.',
      purchaseOrderDate: '10/02/2023',
      validity: '10/02/2023',
      purchaseOrderStatus:PurchaseOrderStatus.Active,
      totalPOAmount:980.00,
      dateLastUpdated:'10/02/2023',
      lastUpdatedUser:'Mike@xyz.com',
      approver: '',
      lastUpdatedBy: '',
      flowStatus: WorkflowStatus.Approved
    },
    {
      purchaseOrderNumber: 'P012349',
      supplierName: 'Abbas & Co.',
      purchaseOrderDate: '20/01/2023',
      validity: '20/01/2023',
      purchaseOrderStatus:PurchaseOrderStatus.Active,
      totalPOAmount:980.00,
      dateLastUpdated:'20/01/2023',
      lastUpdatedUser:'Vikky@xyz.com',
      approver: '',
      lastUpdatedBy: '',
      flowStatus: WorkflowStatus.Approved

    },
  ];

  cols = [
    { header: 'P.O Number', field: 'purchaseOrderNumber' },
    { header: 'Supplier Name', field: 'supplierName' },
    { header: 'P.O Date', field: 'purchaseOrderDate' },
    { header: 'Validity', field: 'validity' },
    { header: 'P.O Status', field: 'purchaseOrderStatus' },
    { header: 'Total P.O Amount', field: 'totalPOAmount' },
    { header: 'Date Last Updated', field: 'dateLastUpdated' },
    { header: 'Last Updated User', field: 'lastUpdatedUser' }
  ];
  selectedPurchaseOrders!: PurchaseOrderInterface[];
  loading: boolean = false;



  //Filters
  purchaseOrderStatusOptions: PurchaseOrderStatusOption[] =
    [{ value: '', viewValue: '' },
    { value: 'Active', viewValue: 'Active' },
    { value: 'Inactive', viewValue: 'Not Active' }]

  clmns = this.cols.map(el => el.header);
  purchaseOrderStatusControl = new FormControl('');
  selectedColumns = new FormControl(this.clmns);

  tableSelectedColumns: any[] = this.cols;

  //pagination
  totalRecords = 100;
  rows = 10;
  rowsPerPageOptions = [10, 25, 50]


  constructor(
    private fb: FormBuilder,
    private titleService: Title,
    private logger: NGXLogger,
    private notificationService: NotificationService,
    public dialog: MatDialog,
    private router: Router,
    private service:PurchaseOrderService) {

    this.initalizeFilter();
    console.log(this.clmns);
    console.log(this.tableSelectedColumns);

    this.selectedColumns.valueChanges.subscribe(el => {
      this.tableSelectedColumns = el ? this.cols.filter(col => el.includes(col.header)) : this.cols;
      console.log(el);
    })

  }
  initalizeFilter() {
    this.filterPurchaseOrderForm = this.fb.group({

      'purchaseOrderNumber': new FormControl(''),
      'supplierName': new FormControl(''),
      'purchaseOrderDate': new FormControl(''),
      'purchaseOrderStatus': this.purchaseOrderStatusControl

    });
  }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - PurchaseOrders');
    this.logger.log('PurchaseOrder loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('PurchaseOrder section loaded!');
    });


  }




  modify(purchaseOrder: PurchaseOrderInterface) {
    console.log(purchaseOrder);
    this.service.selectedPurchaseOrder = purchaseOrder;
    this.router.navigate(['/purchaseOrder/edit']);

  }
  print() { }

  filterPurchaseOrders(formdata: any) {
    console.log(formdata);
  }
  resetFilters() {
    this.initalizeFilter();
  }
  paginate(event: any) {
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages
    console.log(event);
  }

}


interface PurchaseOrderStatusOption {
  value: string;
  viewValue: string;
}





