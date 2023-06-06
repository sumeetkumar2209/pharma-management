import { Component, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { SupplierInterface, SupplierQualificationStatus, SupplierStatus, WorkflowStatus } from 'src/app/features/supplier/supplier.interface';
import { Router } from '@angular/router';
import { SupplierService } from '../supplier.service';
import { SelectionItem } from 'src/app/core/interfaces/interface';
import { STATUS_OPTION, SUPPLIER_HEADERS } from 'src/app/core/constants/constant';



@Component({
  selector: 'app-supplier-page',
  templateUrl: './supplier-page.component.html',
  styleUrls: ['./supplier-page.component.css'],

})
export class SupplierPageComponent {
  filterSupplierForm!: FormGroup;

  animal!: string;
  name!: string;
  suppliers!: SupplierInterface[];

  cols = SUPPLIER_HEADERS;
  selectedSuppliers!: SupplierInterface[];
  loading: boolean = false;

  //Filters
  statusList: SelectionItem[] = STATUS_OPTION;

  clmns = this.cols.filter(el=>el.type==='default').map(el => el.header);
  selectedColumns = new FormControl(this.clmns);

  tableSelectedColumns: any[] = this.cols;

  //pagination
  totalRecords = 100;
  rows = 10;
  rowsPerPageOptions = [10, 25, 50];
  start = 0;
  end = this.rows;
  orderBy!:string;
  orderType!:string;

  constructor(
    private fb: FormBuilder,
    private titleService: Title,
    private logger: NGXLogger,
    private notificationService: NotificationService,
    public dialog: MatDialog,
    private router: Router,
    private service:SupplierService) {

    this.initalizeFilter();
    console.log(this.clmns);
    console.log(this.tableSelectedColumns);
    this.tableSelectedColumns = this.cols.filter(el=>el.type==='default');
    this.selectedColumns.valueChanges.subscribe(el => {
      this.tableSelectedColumns = el ? this.cols.filter(col => el.includes(col.header)) : this.cols;
      console.log(el);
    })

  }
  initalizeFilter() {
    this.filterSupplierForm = this.fb.group({

      'supplierId': new FormControl(null),
      'companyName': new FormControl(null),
      'postalCode': new FormControl(null),
      'supplierStatus': new FormControl(null),
      'reviewStatus': new FormControl(null)

    });
  }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - Suppliers');
    this.fetchSuppliersData();

    setTimeout(() => {
      this.notificationService.openSnackBar('Supplier section loaded!');
    });


  }

  fetchSuppliersData( filter={}){
    this.service.fetchSuppliers({
      endIndex: this.end,
      filter: filter,
      startIndex: this.start,
      orderBy: this.orderBy,
      orderType: this.orderType
    }).
    subscribe((res:any)=>{
      this.suppliers = res.suppliers;
      this.totalRecords = res.totalCount;

    });
  }

  modify(supplier: SupplierInterface) {
    console.log(supplier);
    this.service.selectedSupplier = supplier;
    this.router.navigate(['/supplier/edit']);

  }
  print() { }

  filterSuppliers(formdata: any) {
    console.log(formdata);

    this.fetchSuppliersData(formdata);

  }
  resetFilters() {
    this.initalizeFilter();
  }
  paginate(event: any) {
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages

    this.start = event.first ;
    this.end = this.start + event.rows ;
    this.rows = event.rows;
    this.orderBy = event.sortField;
    this.orderType = event.sortOrder === -1? 'desc':'asc';
    console.log(event);
    this.fetchSuppliersData();
  }

  refreshSuppliers(){
    this.fetchSuppliersData();
  }

}


interface SupplierStatusOption {
  value: string;
  viewValue: string;
}





