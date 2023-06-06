import { Component } from '@angular/core';

import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Router } from '@angular/router';
import { CustomerInterface } from '../customer.interface';
import { CustomerService } from '../customer.service';
import { AuthenticationService } from 'src/app/core/services/auth.service';
import { OptionsInterface, SelectionItem } from 'src/app/core/interfaces/interface';
import * as moment from 'moment';
import { UserDetails } from 'src/app/core/classes/user-details';


@Component({
  selector: 'app-add-customer-page',
  templateUrl: './add-customer-page.component.html',
  styleUrls: ['./add-customer-page.component.css']
})
export class AddCustomerPageComponent {
  addCustomerForm: FormGroup;
  customer: any;
  countryList!: SelectionItem[];
  currencyList!: SelectionItem[];
  statusList!: SelectionItem[];
  qualifyList!: SelectionItem[];
  approverList!: SelectionItem[];
  approvalStatusOption!: SelectionItem[];
  editForm: boolean = false;
  approverForm: boolean = false;
  _customer!: CustomerInterface;

  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger, private notificationService: NotificationService,
    private router: Router,
    private service: CustomerService,
    private authService: AuthenticationService) {
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
      'customerName': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'contactName': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'contactEmail': new FormControl({ value: null, disabled: this.approverForm }, [Validators.required, Validators.email]),
      'contactNumber': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'contactCountry': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'currency': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'status': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'qualification': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'validTillDate': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
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

      this._customer = this.service.selectedCustomer;
      this.populateFormFields();

    }


    const options = this.authService.getCurrentMetadata();
    this.setSelectionOptions(options);

  }

  setSelectionOptions(options: OptionsInterface) {
    this.countryList = options.countryList;
    this.currencyList = options.currencyList;
    this.statusList = options.statusList;
    this.qualifyList = options.qualificationStatusList;
    this.approverList = (this.authService.getCurrentUserDetails() as UserDetails).approverMap;
    this.approvalStatusOption = options.reviewStatusList;
  }
  ngOnInit() {
    console.log(this.editForm);
    this.titleService.setTitle('Reiphy Pharma - Add Customer');
    this.logger.log('Add Customer loaded');

    setTimeout(() => {

      if (this.editForm) {
        this.notificationService.openSnackBar('Edit Customer Form and save to Modify Customer!');

      } else if (this.approverForm) {

        this.notificationService.openSnackBar('Select Approver Status to Approve/Reject Customer!');

      } else {
        this.notificationService.openSnackBar('Fill in the Customer form to Add Customer!');
      }
    });

  }
  populateFormFields() {
    this.addCustomerForm.get('customerId')?.setValue(this._customer.customerId);
    this.addCustomerForm.get('initialAdditionDate')?.setValue(this._customer.initialAdditionDate);
    this.addCustomerForm.get('customerName')?.setValue(this._customer.customerName);
    this.addCustomerForm.get('contactName')?.setValue(this._customer.contactName);
    this.addCustomerForm.get('contactEmail')?.setValue(this._customer.contactEmail);
    this.addCustomerForm.get('contactNumber')?.setValue(this._customer.contactNumber);
    this.addCustomerForm.get('contactCountry')?.setValue(this._customer.country);
    this.addCustomerForm.get('currency')?.setValue(this._customer.currency);
    this.addCustomerForm.get('status')?.setValue(this._customer.customerStatus);
    this.addCustomerForm.get('qualification')?.setValue(this._customer.customerQualificationStatus);
    this.addCustomerForm.get('validTillDate')?.setValue(this._customer.validTillDate);
    this.addCustomerForm.get('address1')?.setValue(this._customer.addressLine1);
    this.addCustomerForm.get('address2')?.setValue(this._customer.addressLine2);
    this.addCustomerForm.get('address3')?.setValue(this._customer.addressLine3);
    this.addCustomerForm.get('town')?.setValue(this._customer.town);
    this.addCustomerForm.get('postcode')?.setValue(this._customer.postalCode);
    this.addCustomerForm.get('approver')?.setValue(this._customer.approver);
  }
  onSubmit(post: any) {
    console.log(post);
    const addCustomerRequest: CustomerInterface = {
      customerId: post.customerId,
      initialAdditionDate: post.initialAdditionDate,
      customerName: post.customerName,
      contactName: post.contactName,
      contactEmail: post.contactEmail,
      contactNumber: post.contactNumber,// +post.contactCountry,
      // contactCountry: post.contactCountry,
      currency: post.currency,
      customerStatus: post.status,
      customerQualificationStatus: post.qualification,
      validTillDate: moment(post.validTillDate).format('YYYY-MM-DD'),
      addressLine1: post.address1,
      addressLine2: post.address2,
      addressLine3: post.address3,
      town: post.town,
      postalCode: post.postcode,
      country: post.country,
      approver: post.approver,
      userId: this.authService.getCurrentUserDetails().userId,
    }

    if (!this.editForm && !this.approverForm) {
      this.service.addCustomer(addCustomerRequest).subscribe(res => {
        console.log(res);

        setTimeout(() => {
          this.notificationService.openSnackBar('Customer Added and sent for Approval. Redirecting to customer page');
        });
        setTimeout(() => {
          this.addCustomerForm.reset()
          this.router.navigate(['/customer']);
        }, 2000);
      });
    } else if (this.editForm) {
      this.service.editCustomer(addCustomerRequest).subscribe(res => {
        console.log(res);
        setTimeout(() => {
          this.notificationService.openSnackBar('Customer Modified and sent for Approval. Redirecting to customer page');
        });
        setTimeout(() => {
          this.addCustomerForm.reset()
          this.router.navigate(['/customer']);
        }, 2000);
      });
    } else {
      const requestBody = {
        customerId: this._customer.customerId,
        approvalStatus: post.approvalStatus,
        approvalComments: post.approvalComments
      }
      const approvalStatus = post.approvalStatus;
      const approvalComments = post.approvalComments;
      this.service.approveCustomer(requestBody).subscribe(res => {
        console.log(res);
        setTimeout(() => {
          this.notificationService.openSnackBar('Customer Modified and sent for Approval. Redirecting to customer page');
        });
        setTimeout(() => {
          this.addCustomerForm.reset()
          this.router.navigate(['/customer']);
        }, 2000);
      });
    }

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

  cancelAction() {
    if (this.approverForm) {
      this.router.navigate(['/customer/pending']);
    } else {
      this.router.navigate(['/customer']);
    }
  }

}
