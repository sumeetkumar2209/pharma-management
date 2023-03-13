import { Component, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';
import { CustomerInterface, CustomerQualificationStatus, CustomerStatus, WorkflowStatus } from '../customer.interface';



@Component({
  selector: 'app-customer-page',
  templateUrl: './customer-page.component.html',
  styleUrls: ['./customer-page.component.css'],

})
export class CustomerPageComponent {
  filterCustomerForm!: FormGroup;

  animal!: string;
  name!: string;
  customers: CustomerInterface[] = [
    {
      customerId: 101,
      companyName: 'MEDLEY PHARMACEUTICAL LTD',
      contactName: 'Vikas',
      contactNumber: 9999999,
      contactEmail: 'v@yahoo.com',
      country: 'UK',
      currency: 'GBP',
      customerQualificationStatus: CustomerQualificationStatus.Qualified,

      validTill: 'Dec 31 2023',
      customerStatus: CustomerStatus.Active,
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
      customerId: 102,
      companyName: 'RICHI PHARMACEUTICAL LTD',
      contactName: 'Prakash',
      contactNumber: 11111111,
      contactEmail: 'p@gamil.com',
      country: 'USA',
      currency: 'USD',
      customerQualificationStatus: CustomerQualificationStatus.Qualified,

      validTill: 'Dec 31 2030',
      customerStatus: CustomerStatus.Active,
      addressLine1: '',
      town: '',
      postalCode: '',
      approver: 'Vijay',
      userId: '',
      initialAdditionDate: 'Feb 1 2023',
      lastUpdatedBy: '',
      flowStatus: WorkflowStatus.Approved

    },
  ];

  cols = [
    { header: 'Customer Id', field: 'customerId' },
    { header: 'Company Name', field: 'companyName' },
    { header: 'Contact Name', field: 'contactName' },
    { header: 'Contact Email', field: 'contactEmail' },
    { header: 'Contact Number', field: 'contactNumber' },
    { header: 'Country', field: 'country' },
    { header: 'Currency', field: 'currency' },
    { header: 'Status', field: 'qualificationStatus' },
    { header: 'Valid Till', field: 'validTill' },
  ];
  selectedCustomers!: CustomerInterface[];
  loading: boolean = false;



  //Filters
  customerStatusOptions: CustomerStatusOption[] =
    [{ value: '', viewValue: '' },
    { value: 'Active', viewValue: 'Active' },
    { value: 'Inactive', viewValue: 'Not Active' }]

  clmns = this.cols.map(el => el.header);
  customerStatusControl = new FormControl('');
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
    private service:CustomerService) {

    this.initalizeFilter();
    console.log(this.clmns);
    console.log(this.tableSelectedColumns);

    this.selectedColumns.valueChanges.subscribe(el => {
      this.tableSelectedColumns = el ? this.cols.filter(col => el.includes(col.header)) : this.cols;
      console.log(el);
    })

  }
  initalizeFilter() {
    this.filterCustomerForm = this.fb.group({

      'customerId': new FormControl(''),
      'customerName': new FormControl(''),
      'postalCode': new FormControl(''),
      'customerStatusControl': this.customerStatusControl

    });
  }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - Customers');
    this.logger.log('Customer loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Customer section loaded!');
    });


  }




  modify(customer: CustomerInterface) {
    console.log(customer);
    this.service.selectedCustomer = customer;
    this.router.navigate(['/customer/edit']);

  }
  print() { }

  filterCustomers(formdata: any) {
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


interface CustomerStatusOption {
  value: string;
  viewValue: string;
}





