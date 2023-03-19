import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Router } from '@angular/router';
import { SupplierInterface } from '../supplier.interface';
import { SupplierService } from '../supplier.service';
import { AuthenticationService } from 'src/app/core/services/auth.service';
import { SelectionItem } from 'src/app/core/interfaces/interface';
import { APPROVAL_STATUS_OPTION, COUNTRY_OPTION, CURRENCY_OPTION, QUALIFICATION_STATUS_OPTION, STATUS_OPTION } from 'src/app/core/constants/constant';
import * as moment from 'moment';


@Component({
  selector: 'app-add-supplier-page',
  templateUrl: './add-supplier-page.component.html',
  styleUrls: ['./add-supplier-page.component.css']
})
export class AddSupplierPageComponent {
  addSupplierForm: FormGroup;
  supplier: any;
  countryList: SelectionItem[] = COUNTRY_OPTION;
  currencyList: SelectionItem[] = CURRENCY_OPTION;
  statusList: SelectionItem[] = STATUS_OPTION;
  qualifyList: SelectionItem[] = QUALIFICATION_STATUS_OPTION;
  approverList: SelectionItem[] = [{ label: 'Vijay', value: '009e8ce0afcd4d1098ff7e26dbb2755c' }];
  approvalStatusOption: SelectionItem[] = APPROVAL_STATUS_OPTION;
  editForm: boolean = false;
  approverForm: boolean = false;
  _supplier!: SupplierInterface;

  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger, private notificationService: NotificationService,
    private router: Router,
    private service: SupplierService,
    private authService: AuthenticationService) {
    if (this.router.url.includes('edit')) {
      this.editForm = true;
      this.approverForm = false;

    } else if (this.router.url.includes('approve')) {
      this.editForm = false;
      this.approverForm = true;
    }
    this.addSupplierForm = this.fb.group({
      'supplierId': new FormControl({ value: null, disabled: this.approverForm || this.editForm }),
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
      this._supplier = this.service.selectedSupplier;
      this.populateFormFields();
    } else if (this.approverForm) {



    }

    console.log(this.router.url);
  }
  ngOnInit() {
    console.log(this.editForm);
    this.titleService.setTitle('Reiphy Pharma - Add Supplier');
    this.logger.log('Add Supplier loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Fill in the form to Add Supplier!');
    });

  }
  populateFormFields() {
    this.addSupplierForm.get('supplierId')?.setValue(this._supplier.supplierId);
    this.addSupplierForm.get('initialAdditionDate')?.setValue(this._supplier.initialAdditionDate);
    this.addSupplierForm.get('companyName')?.setValue(this._supplier.companyName);
    this.addSupplierForm.get('contactName')?.setValue(this._supplier.contactName);
    this.addSupplierForm.get('contactEmail')?.setValue(this._supplier.contactEmail);
    this.addSupplierForm.get('contactNumber')?.setValue(this._supplier.contactNumber);
    this.addSupplierForm.get('contactCountry')?.setValue(this._supplier.country);
    this.addSupplierForm.get('currency')?.setValue(this._supplier.currency);
    this.addSupplierForm.get('status')?.setValue(this._supplier.supplierStatus);
    this.addSupplierForm.get('qualification')?.setValue(this._supplier.supplierQualificationStatus);
    this.addSupplierForm.get('validTill')?.setValue(this._supplier.validTillDate);
    this.addSupplierForm.get('address1')?.setValue(this._supplier.addressLine1);
    this.addSupplierForm.get('address2')?.setValue(this._supplier.addressLine2);
    this.addSupplierForm.get('address3')?.setValue(this._supplier.addressLine3);
    this.addSupplierForm.get('town')?.setValue(this._supplier.town);
    this.addSupplierForm.get('postcode')?.setValue(this._supplier.postalCode);
    this.addSupplierForm.get('approver')?.setValue(this._supplier.approvedBy);
  }
  onSubmit(post: any) {
    console.log(post);
    const addSupplierRequest: SupplierInterface = {
      supplierId: post.supplierId,
      initialAdditionDate: post.initialAdditionDate,
      companyName: post.companyName,
      contactName: post.contactName,
      contactEmail: post.contactEmail,
      contactNumber: post.contactNumber,// +post.contactCountry,
      // contactCountry: post.contactCountry,
      currency: post.currency,
      supplierStatus: post.status,
      supplierQualificationStatus: post.qualification,
      validTillDate: moment(post.validTill).format('YYYY-MM-DD'),
      addressLine1: post.address1,
      addressLine2: post.address2,
      addressLine3: post.address3,
      town: post.town,
      postalCode: post.postcode,
      country: post.country,
      approvedBy: post.approver,
      userId: this.authService.getCurrentUserDetails().userId,
    }

    if (!this.editForm && !this.approverForm) {
      this.service.addSupplier(addSupplierRequest).subscribe(res => {
        console.log(res);

        setTimeout(() => {
          this.notificationService.openSnackBar('Supplier Added and sent for Approval. Redirecting to supplier page');
        });
        setTimeout(() => {
          this.addSupplierForm.reset()
          this.router.navigate(['/supplier']);
        }, 2000);
      });
    } else if (this.editForm) {
      this.service.editSupplier(addSupplierRequest).subscribe(res => {
        console.log(res);
        setTimeout(() => {
          this.notificationService.openSnackBar('Supplier Modified and sent for Approval. Redirecting to supplier page');
        });
        setTimeout(() => {
          this.addSupplierForm.reset()
          this.router.navigate(['/supplier']);
        }, 2000);
      });
    } else {

    }

  }

  get apControls(): { [key: string]: AbstractControl } {
    return this.addSupplierForm.controls;
  }

  getError(controlName: string) {
    return this.apControls[controlName].hasError('required') ? 'Field is required' :
      this.apControls[controlName].hasError('pattern') ? 'Not a valid pattern' :
        '';
  }

  save() { }

}
