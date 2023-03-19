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
import { STATUS_OPTION } from 'src/app/core/constants/constant';



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
 
  //  = [
  //   {
  //     supplierId: 101,
  //     companyName: 'MEDLEY PHARMACEUTICAL LTD',
  //     contactName: 'Vikas',
  //     contactNumber: 9999999,
  //     contactEmail: 'v@yahoo.com',
  //     country: 'UK',
  //     currency: 'GBP',
  //     supplierQualificationStatus: SupplierQualificationStatus.Qualified,

  //     validTill: 'Dec 31 2023',
  //     supplierStatus: SupplierStatus.Active,
  //     addressLine1: '',
  //     town: '',
  //     postalCode: '',
  //     approver: '',
  //     userId: '',
  //     initialAdditionDate: '',
  //     lastUpdatedBy: '',
  //     flowStatus: WorkflowStatus.Approved
  //   },
  //   {
  //     supplierId: 102,
  //     companyName: 'RICHI PHARMACEUTICAL LTD',
  //     contactName: 'Prakash',
  //     contactNumber: 11111111,
  //     contactEmail: 'p@gamil.com',
  //     country: 'USA',
  //     currency: 'USD',
  //     supplierQualificationStatus: SupplierQualificationStatus.Qualified,

  //     validTill: 'Dec 31 2030',
  //     supplierStatus: SupplierStatus.Active,
  //     addressLine1: '',
  //     town: '',
  //     postalCode: '',
  //     approver: 'Vijay',
  //     userId: '',
  //     initialAdditionDate: 'Feb 1 2023',
  //     lastUpdatedBy: '',
  //     flowStatus: WorkflowStatus.Approved

  //   },
  // ];

  cols = [
    { header: 'Supplier Id', field: 'supplierId' },
    { header: 'Company Name', field: 'companyName' },
    { header: 'Contact Name', field: 'contactName' },
    { header: 'Contact Email', field: 'contactEmail' },
    { header: 'Contact Number', field: 'contactNumber' },
    { header: 'Country', field: 'country' },
    { header: 'Currency', field: 'currency' },
    { header: 'Status', field: 'qualificationStatus' },
    { header: 'Valid Till', field: 'validTill' },
  ];
  selectedSuppliers!: SupplierInterface[];
  loading: boolean = false;



  //Filters
  statusList: SelectionItem[] = STATUS_OPTION;

  clmns = this.cols.map(el => el.header);
  selectedColumns = new FormControl(this.clmns);

  tableSelectedColumns: any[] = this.cols;

  //pagination
  totalRecords = 100;
  rows = 10;
  rowsPerPageOptions = [10, 25, 50];
  start = 0;
  end = this.rows;


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
      startIndex: this.start
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
    console.log(event);
  }

  refreshSuppliers(){
    this.fetchSuppliersData();
  }

}


interface SupplierStatusOption {
  value: string;
  viewValue: string;
}





