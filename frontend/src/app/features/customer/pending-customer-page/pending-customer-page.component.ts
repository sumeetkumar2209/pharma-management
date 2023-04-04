import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { CustomerInterface } from 'src/app/features/customer/customer.interface';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';
import { SelectionItem } from 'src/app/core/interfaces/interface';
import { STATUS_OPTION } from 'src/app/core/constants/constant';



@Component({
  selector: 'app-pending-customer-page',
  templateUrl: './pending-customer-page.component.html',
  styleUrls: ['./pending-customer-page.component.css'],

})
export class PendingCustomerPageComponent {
  filterCustomerForm!: FormGroup;

  animal!: string;
  name!: string;
  customers!: CustomerInterface[];

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
    private service: CustomerService) {

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

      'customerId': new FormControl(null),
      'companyName': new FormControl(null),
      'postalCode': new FormControl(null),
      'customerStatus': new FormControl(null),
      'reviewStatus': new FormControl(null)

    });
  }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - Customers');
    this.fetchPendingCustomersData();

    setTimeout(() => {
      this.notificationService.openSnackBar('Customer Workflow section loaded!');
    });


  }

  fetchPendingCustomersData(filter = {}) {
    this.service.fetchPendingCustomers({
      endIndex: this.end,
      filter: filter,
      startIndex: this.start
    }).
      subscribe((res: any) => {
        this.customers = res.customers;
        this.totalRecords = res.totalCount;

      });
  }

  approve(customer: CustomerInterface) {
    console.log(customer);
    this.service.selectedCustomer = customer;
    this.router.navigate(['/customer/approve']);

  }
  modify(customer: CustomerInterface) {
    console.log(customer);
    this.service.selectedCustomer = customer;
    this.router.navigate(['/customer/edit']);

  }
  print() { }

  filterCustomers(formdata: any) {
    console.log(formdata);

    this.fetchPendingCustomersData(formdata);

  }
  resetFilters() {
    this.initalizeFilter();
  }
  paginate(event: any) {
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages

    this.start = event.first;
    this.end = this.start + event.rows;
    this.rows = event.rows;
    console.log(event);
  }

  refreshCustomers() {
    this.fetchPendingCustomersData();
  }

}


interface CustomerStatusOption {
  value: string;
  viewValue: string;
}





