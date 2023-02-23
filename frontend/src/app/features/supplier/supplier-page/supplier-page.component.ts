import { Component, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {SupplierInterface, SupplierQualificationStatus, SupplierStatus, WorkflowStatus} from 'src/app/features/supplier/supplier.interface';



@Component({
  selector: 'app-supplier-page',
  templateUrl: './supplier-page.component.html',
  styleUrls: ['./supplier-page.component.css'],

})
export class SupplierPageComponent {
  filterSupplierForm!: FormGroup;

  animal!: string;
  name!: string;
  suppliers: SupplierInterface[] = [
    {
      supplierId: 101,
      companyName: 'MEDLEY PHARMACEUTICAL LTD',
      contactName: 'Vikas',
      contactNumber: 9999999,
      contactEmail: 'v@yahoo.com',
      country: 'UK',
      currency: 'GBP',
      supplierQualificationStatus: SupplierQualificationStatus.Qualified,

      validTill: 'Dec 31 2023',
      supplierStatus: SupplierStatus.Active,
      addressLine1: '',
      town: '',
      postalCode: '',
      approver: '',
      userId: '',
      initialAdditionDate: '',
      lastUpdatedBy: '',
      flowStatus: WorkflowStatus.Approved
    },
    {
      supplierId: 102,
      companyName: 'RICHI PHARMACEUTICAL LTD',
      contactName: 'Prakash',
      contactNumber: 11111111,
      contactEmail: 'p@gamil.com',
      country: 'USA',
      currency: 'USD',
      supplierQualificationStatus: SupplierQualificationStatus.Qualified,

      validTill: 'Dec 31 2030',
      supplierStatus: SupplierStatus.Active,
      addressLine1: '',
      town: '',
      postalCode: '',
      approver: '',
      userId: '',
      initialAdditionDate: '',
      lastUpdatedBy: '',
      flowStatus: WorkflowStatus.Approved

    },
  ];

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
  supplierStatusOptions: SupplierStatusOption[] = 
  [{value: '', viewValue: ''},
  {value: 'Active', viewValue: 'Active'},
  {value: 'Inactive', viewValue: 'Not Active'}]

  clmns = this.cols.map(el => el.header);
  supplierStatusControl=new FormControl('');
  selectedColumns = new FormControl(this.clmns);

  tableSelectedColumns:any[] = this.cols;

  //pagination
  totalRecords=100;
  rows = 10;
  rowsPerPageOptions=[10,25,50]


  constructor(private fb: FormBuilder,private titleService: Title,
    private logger: NGXLogger,
    private notificationService: NotificationService,
    public dialog: MatDialog) {

    this.initalizeFilter();
    console.log(this.clmns);
    console.log(this.tableSelectedColumns);
    
    this.selectedColumns.valueChanges.subscribe(el=>{
      this.tableSelectedColumns = el?this.cols.filter(col=>el.includes(col.header)):this.cols;
      console.log(el);
    })
   
  }
  initalizeFilter(){
    this.filterSupplierForm = this.fb.group({
    
      'supplierId': new FormControl(''),
      'supplierName': new FormControl(''),
      'postalCode': new FormControl(''),
      'supplierStatusControl':this.supplierStatusControl
     
    });
  }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - Suppliers');
    this.logger.log('Supplier loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Supplier section loaded!');
    });


  }




  modify(supplier: SupplierInterface) {
    console.log(supplier);
  }
  print() { }

  filterSuppliers(formdata:any){
    console.log(formdata);
  }
  resetFilters(){
    this.initalizeFilter();
  }
  paginate(event:any) {
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages
    console.log(event);
}
 
}


interface SupplierStatusOption {
  value: string;
  viewValue: string;
}





