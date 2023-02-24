import { Component, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ProductService } from '../product.service';
import { ProductInterface, ProductStatus, WorkflowStatus } from '../product.interface';



@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css'],

})
export class ProductPageComponent {
  filterProductForm!: FormGroup;

  animal!: string;
  name!: string;
  products: ProductInterface[] = [
    {
      productId: 'P101',
      productName: 'Parsacol',
      productManufacturer: 'Pfizer',
      productManufacturerCountry: 'UK',
      licenseNumber: 950023,
      productStatus: ProductStatus.Active,
      productApprovingAuthority: 'WHO',
      dosageForm:'tablets',
      packType:'Strip',
      packSize:50,
      pricePerPack:40,
      taxPercentage:10,
      taxationType:'VAT',
      strength:'500mg',
      currency: 'GBP',
      productPhoto: 's3.image.001.jpg',
      approver: 'Anna Marie',
      initialAdditionDate: '2023-02-02',
      lastUpdatedBy: '101',
      flowStatus: WorkflowStatus.Approved
    },
    {
      productId: 'P102',
      productName: 'Parsacol2',
      productManufacturer: 'Moderna',
      productManufacturerCountry: 'IN',
      licenseNumber: 950023,
      productStatus: ProductStatus.Active,
      productApprovingAuthority: 'IMO',
      dosageForm:'Capsule',
      packType:'Box',
      packSize:50,
      pricePerPack:40,
      taxPercentage:10,
      taxationType:'VAT',
      strength:'200mg',
      currency: 'INR',
      productPhoto: 's3.image.002.jpg',
      approver: 'Anna Polina',
      initialAdditionDate: '2023-02-02',
      lastUpdatedBy: '101',
      flowStatus: WorkflowStatus.Approved

    },
  ];

  cols = [
    { header: 'Product Id', field: 'productId' },
    { header: 'Product Name', field: 'productName' },
    { header: 'License No.', field: 'licenseNumber' },
    { header: 'Dosage Form', field: 'dosageForm' },
    { header: 'Product Manufacturer', field: 'productManufacturer' },
    { header: 'Pack Type', field: 'packType' },
    { header: 'Pack Size', field: 'packSize' },
    { header: 'Proce per Pack', field: 'pricePerPack' },
    { header: 'Currency', field: 'currency' },
  ];
  selectedProducts!: ProductInterface[];
  loading: boolean = false;



  //Filters
  productStatusOptions: ProductStatusOption[] =
    [{ value: '', viewValue: '' },
    { value: 'Active', viewValue: 'Active' },
    { value: 'Inactive', viewValue: 'Not Active' }]

  clmns = this.cols.map(el => el.header);
  productStatusControl = new FormControl('');
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
    private service:ProductService) {

    this.initalizeFilter();
    console.log(this.clmns);
    console.log(this.tableSelectedColumns);

    this.selectedColumns.valueChanges.subscribe(el => {
      this.tableSelectedColumns = el ? this.cols.filter(col => el.includes(col.header)) : this.cols;
      console.log(el);
    })

  }
  initalizeFilter() {
    this.filterProductForm = this.fb.group({

      'productId': new FormControl(''),
      'productName': new FormControl(''),
      'postalCode': new FormControl(''),
      'productStatusControl': this.productStatusControl

    });
  }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - Products');
    this.logger.log('Product loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Product section loaded!');
    });


  }




  modify(product: ProductInterface) {
    console.log(product);
    this.service.selectedProduct = product;
    this.router.navigate(['/product/edit']);

  }
  print() { }

  filterProducts(formdata: any) {
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


interface ProductStatusOption {
  value: string;
  viewValue: string;
}





