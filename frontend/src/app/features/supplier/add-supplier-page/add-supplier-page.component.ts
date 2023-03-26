import { Component, Inject, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Router } from '@angular/router';
import { SupplierInterface } from '../supplier.interface';
import { SupplierService } from '../supplier.service';
import { AuthenticationService } from 'src/app/core/services/auth.service';
import { OptionsInterface, SelectionItem } from 'src/app/core/interfaces/interface';
import * as moment from 'moment';
import { UserDetails } from 'src/app/core/classes/user-details';
import { MatDialog } from '@angular/material/dialog';
import { NotificationDialogComponent } from 'src/app/shared/notification-dialog/notification-dialog.component';


@Component({
  selector: 'app-add-supplier-page',
  templateUrl: './add-supplier-page.component.html',
  styleUrls: ['./add-supplier-page.component.css']
})
export class AddSupplierPageComponent implements OnDestroy {
  addSupplierForm: FormGroup;
  supplier: any;
  countryList!: SelectionItem[];
  currencyList!: SelectionItem[];
  statusList!: SelectionItem[];
  qualifyList!: SelectionItem[];
  approverList!: SelectionItem[];
  approvalStatusOption!: SelectionItem[];
  editForm: boolean = false;
  approverForm: boolean = false;
  _supplier!: SupplierInterface;

  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger, private notificationService: NotificationService,
    private router: Router,
    private service: SupplierService,
    private authService: AuthenticationService,
    public dialog: MatDialog,
    @Inject('LOCALSTORAGE') private localStorage: Storage) {

    if (this.router.url.includes('edit')) {
      this.editForm = true;
      this.approverForm = false;
      this.getSetCurrentSupplier();

    } else if (this.router.url.includes('approve')) {
      this.editForm = false;
      this.approverForm = true;
      this.getSetCurrentSupplier();

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

      this._supplier = this.service.selectedSupplier;
      this.populateFormFields();

    }

    const options = this.authService.getCurrentMetadata();
    this.setSelectionOptions(options);

  }
  getSetCurrentSupplier() {
    if (!this._supplier) {
      const cs = this.localStorage.getItem('currentSupplier');
      if (cs) {
        this._supplier = JSON.parse(cs);
      }
    } else {
      this.localStorage.setItem('currentSupplier', JSON.stringify(this._supplier));
    }
  }
  ngOnDestroy(): void {
    this.localStorage.removeItem('currentSupplier');
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
    this.titleService.setTitle('Reiphy Pharma - Add Supplier');
    this.logger.log('Add Supplier loaded');

    setTimeout(() => {

      if (this.editForm) {
        this.notificationService.openSnackBar('Edit Supplier Form and save to Modify Supplier!');

      } else if (this.approverForm) {

        this.notificationService.openSnackBar('Select Approver Status to Approve/Reject Supplier!');

      } else {
        this.notificationService.openSnackBar('Fill in the Supplier form to Add Supplier!');
      }
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
    this.addSupplierForm.get('validTill')?.setValue(moment(this._supplier.validTillDate, 'YYYY-MM-DD').toDate());
    this.addSupplierForm.get('address1')?.setValue(this._supplier.addressLine1);
    this.addSupplierForm.get('address2')?.setValue(this._supplier.addressLine2);
    this.addSupplierForm.get('address3')?.setValue(this._supplier.addressLine3);
    this.addSupplierForm.get('town')?.setValue(this._supplier.town);
    this.addSupplierForm.get('postcode')?.setValue(this._supplier.postalCode);
    this.addSupplierForm.get('approver')?.setValue(this._supplier.approver);
  }
  onSubmit(post: any) {
    console.log(post);
    const addSupplierRequest: SupplierInterface = {
      supplierId: this._supplier ? this._supplier.supplierId : null,

      initialAdditionDate: this._supplier ? this._supplier.initialAdditionDate : null,
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
      approver: post.approver,
      userId: this.authService.getCurrentUserDetails().userId,
    }

    if (!this.editForm && !this.approverForm) {
      this.service.addSupplier(addSupplierRequest).subscribe(res => {
        console.log(res);
        this.openNotificationDialogue(`Add Supplier Successful!`, `Workflow Id ${''} has been created and awaiting approval`);

        setTimeout(() => {
          this.notificationService.openSnackBar('Supplier Added and sent for Approval. Redirecting to supplier page');

        });

      });
    } else if (this.editForm) {
      this.service.editSupplier(addSupplierRequest).subscribe(res => {
        console.log(res);
        setTimeout(() => {
          this.notificationService.openSnackBar('Supplier Modified and sent for Approval. Redirecting to supplier page');
        });
        this.openNotificationDialogue(`Modify Supplier Successful!`, `Supplier Id ${this._supplier.supplierId} has been updated and awaiting approval`);

      });
    } else {
      const requestBody = {
        workFlowId: this._supplier.workFlowId,
        decision: post.approvalStatus,
        comments: post.approvalComments
      }
      const approvalStatus = post.approvalStatus;
      const approvalComments = post.approvalComments;
      this.service.approveSupplier(requestBody).subscribe(res => {
        console.log(res);
        const decision = this.approvalStatusOption.find(el=>el.value === post.approvalStatus)?.label;
        this.openNotificationDialogue(`Approve Supplier Successful!`, `Supplier Id ${this._supplier.supplierId} has been ${decision}`);

        setTimeout(() => {
          this.notificationService.openSnackBar('Supplier Workflow actioned. Redirecting to supplier page');
        });
       
      });
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
  cancelAction() {
    if (this.approverForm) {
      this.router.navigate(['/supplier/pending']);
    } else {
      this.router.navigate(['/supplier']);
    }
  }

  openNotificationDialogue(title:string, message:string){
    const dialogRef = this.dialog.open(NotificationDialogComponent, {
      data: { title: title , message: message },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.addSupplierForm.reset()
      this.router.navigate(['/supplier']);
    });
  }
}
