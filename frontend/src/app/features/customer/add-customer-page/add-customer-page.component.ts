import { Component, ViewChild } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerInterface } from '../customer.interface';
import { CustomerService } from '../customer.service';


@Component({
  selector: 'app-add-customer-page',
  templateUrl: './add-customer-page.component.html',
  styleUrls: ['./add-customer-page.component.css']
})
export class AddCustomerPageComponent {
  addCustomerForm: FormGroup;
  customer: any;
  countryList: String[] = ['IN', 'UK', 'USA'];
  currencyList: String[] = ['INR', 'USD', 'GBP'];
  statusList: String[] = ['Active', 'Inactive'];
  qualifyList: String[] = ['QUALIFIED', 'NOT-QUALIFIED'];
  approverList: String[] = ['Vijay', 'Prakash'];
  approvalStatusOption: String[] = ['Approved', 'Rejected'];
  editForm: boolean = false;
  approverForm: boolean = false;
  _customer!: CustomerInterface;

  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger, private notificationService: NotificationService,
    private router: Router,
    private service: CustomerService) {
    if (this.router.url.includes('edit')) {
      this.editForm = true;
      this.approverForm = false;

    } else if (this.router.url.includes('approve')) {
      this.editForm = false;
      this.approverForm = true;
    }
    this.addCustomerForm = this.fb.group({
      'customerId': new FormControl({ value: null, disabled: this.approverForm || this.editForm }),
      'initialAdditionDate': new FormControl({ value: null, disabled: this.approverForm || this.editForm }),
      'companyName': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'contactName': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'contactEmail': new FormControl({ value: null, disabled: this.approverForm }, [Validators.required, Validators.email]),
      'contactNumber': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'contactCountry': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'currency': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'status': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'qualification': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'validTill': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'address1': new FormControl({ value: null, disabled: this.approverForm }),
      'address2': new FormControl({ value: null, disabled: this.approverForm }),
      'address3': new FormControl({ value: null, disabled: this.approverForm }),
      'town': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'postcode': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'country': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'approver': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'approvalStatus': new FormControl({ value: null, disabled: !this.approverForm }),
      'approvalComments': new FormControl({ value: null, disabled: !this.approverForm }),

    });

    if (this.editForm) {
      this._customer = this.service.selectedCustomer;
      this.populateFormFields();
    } else if (this.approverForm) {



    }

    console.log(this.router.url);
  }
  ngOnInit() {
    console.log(this.editForm);
    this.titleService.setTitle('Reiphy Pharma - Add Customer');
    this.logger.log('Add Customer loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Fill in the form to Add Customer!');
    });

  }
  populateFormFields() {
    this.addCustomerForm.get('customerId')?.setValue(this._customer.customerId);
    this.addCustomerForm.get('initialAdditionDate')?.setValue(this._customer.initialAdditionDate);
    this.addCustomerForm.get('companyName')?.setValue(this._customer.companyName);
    this.addCustomerForm.get('contactName')?.setValue(this._customer.contactName);
    this.addCustomerForm.get('contactEmail')?.setValue(this._customer.contactEmail);
    this.addCustomerForm.get('contactNumber')?.setValue(this._customer.contactNumber);
    this.addCustomerForm.get('contactCountry')?.setValue(this._customer.country);
    this.addCustomerForm.get('currency')?.setValue(this._customer.currency);
    this.addCustomerForm.get('status')?.setValue(this._customer.customerStatus);
    this.addCustomerForm.get('qualification')?.setValue(this._customer.customerQualificationStatus);
    this.addCustomerForm.get('validTill')?.setValue(this._customer.validTill);
    this.addCustomerForm.get('address1')?.setValue(this._customer.addressLine1);
    this.addCustomerForm.get('address2')?.setValue(this._customer.addressLine2);
    this.addCustomerForm.get('address3')?.setValue(this._customer.addressLine3);
    this.addCustomerForm.get('town')?.setValue(this._customer.town);
    this.addCustomerForm.get('postcode')?.setValue(this._customer.postalCode);
    this.addCustomerForm.get('approver')?.setValue(this._customer.approver);
  }
  onSubmit(post: any) {
    this.customer = post;
  }

  get apControls(): { [key: string]: AbstractControl } {
    return this.addCustomerForm.controls;
  }

  getError(controlName: string) {
    return this.apControls[controlName].hasError('required') ? 'Field is required' :
      this.apControls[controlName].hasError('pattern') ? 'Not a valid pattern' :
        '';
  }

  save() { }

}
