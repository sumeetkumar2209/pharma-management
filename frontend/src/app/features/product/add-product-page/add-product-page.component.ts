import { Component, ViewChild } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';

import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductInterface } from '../product.interface';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-add-product-page',
  templateUrl: './add-product-page.component.html',
  styleUrls: ['./add-product-page.component.css']
})
export class AddProductPageComponent {
  addProductForm: FormGroup;
  product: any;
  countryList: String[] = ['IN', 'UK', 'USA'];
  currencyList: String[] = ['INR', 'USD', 'GBP'];
  statusList: String[] = ['Active', 'Inactive'];
  qualifyList: String[] = ['QUALIFIED', 'NOT-QUALIFIED'];
  approverList: String[] = ['Vijay', 'Prakash'];
  approvalStatusOption: String[] = ['Approved', 'Rejected'];
  editForm: boolean = false;
  approverForm: boolean = false;
  _product!: ProductInterface;


  //file upload
  currentFile?: File;
  progress = 0;
  message = '';

  fileName = 'Select File';
  fileInfos?: Observable<any>;

  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger, private notificationService: NotificationService,
    private router: Router,
    private service: ProductService) {
    if (this.router.url.includes('edit')) {
      this.editForm = true;
      this.approverForm = false;

    } else if (this.router.url.includes('approve')) {
      this.editForm = false;
      this.approverForm = true;
    }
    this.addProductForm = this.fb.group({
      'productId': new FormControl({ value: null, disabled: this.approverForm || this.editForm }),
      'initialAdditionDate': new FormControl({ value: null, disabled: this.approverForm || this.editForm }),
      'productManufacturer': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'productName': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'productManufacturerCountry': new FormControl({ value: null, disabled: this.approverForm }, [Validators.required, Validators.email]),
      'licenseNumber': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'productApprovingAuthority': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'dosageForm': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'packType': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'productStatus': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'packSize': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'pricePerPack': new FormControl({ value: null, disabled: this.approverForm }),
      'taxPercentage': new FormControl({ value: null, disabled: this.approverForm }),
      'taxationType': new FormControl({ value: null, disabled: this.approverForm }),
      'strength': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'currency': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'productPhoto': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'approver': new FormControl({ value: null, disabled: this.approverForm }, Validators.required),
      'approvalStatus': new FormControl({ value: null, disabled: !this.approverForm }),
      'approvalComments': new FormControl({ value: null, disabled: !this.approverForm }),

    });

    if (this.editForm) {
      this._product = this.service.selectedProduct;
      this.populateFormFields();
    } else if (this.approverForm) {



    }

    console.log(this.router.url);
  }
  ngOnInit() {
    console.log(this.editForm);
    this.titleService.setTitle('Reiphy Pharma - Add Product');
    this.logger.log('Add Product loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Fill in the form to Add Product!');
    });

  }
  populateFormFields() {
    this.addProductForm.get('productId')?.setValue(this._product.productId);
    this.addProductForm.get('initialAdditionDate')?.setValue(this._product.initialAdditionDate);
    this.addProductForm.get('initialAdditionDate')?.setValue(this._product.initialAdditionDate);
    this.addProductForm.get('producinitialAdditionDatetName')?.setValue(this._product.initialAdditionDate);
    this.addProductForm.get('productManufacturerCountry')?.setValue(this._product.productManufacturerCountry);
    this.addProductForm.get('licenseNumber')?.setValue(this._product.licenseNumber);
    this.addProductForm.get('productApprovingAuthority')?.setValue(this._product.productApprovingAuthority);
    this.addProductForm.get('dosageForm')?.setValue(this._product.dosageForm);
    this.addProductForm.get('packType')?.setValue(this._product.packType);
    this.addProductForm.get('productStatus')?.setValue(this._product.productStatus);
    this.addProductForm.get('packSize')?.setValue(this._product.packSize);
    this.addProductForm.get('pricePerPack')?.setValue(this._product.pricePerPack);
    this.addProductForm.get('taxPercentage')?.setValue(this._product.taxPercentage);
    this.addProductForm.get('taxationType')?.setValue(this._product.taxationType);
    this.addProductForm.get('strength')?.setValue(this._product.strength);
    this.addProductForm.get('currency')?.setValue(this._product.currency);
    this.addProductForm.get('productPhoto')?.setValue(this._product.productPhoto);
    this.addProductForm.get('approver')?.setValue(this._product.approver);

  }
  onSubmit(post: any) {
    this.product = post;
  }

  get apControls(): { [key: string]: AbstractControl } {
    return this.addProductForm.controls;
  }

  getError(controlName: string) {
    return this.apControls[controlName].hasError('required') ? 'Field is required' :
      this.apControls[controlName].hasError('pattern') ? 'Not a valid pattern' :
        '';
  }

  save() { }

  selectFile(event: any): void {
    if (event.target.files && event.target.files[0]) {
      const file: File = event.target.files[0];
      this.currentFile = file;
      this.fileName = this.currentFile.name;
    } else {
      this.fileName = 'Select File';
    }
  }

  upload(): void {
    this.progress = 0;
    this.message = "";

    if (this.currentFile) {
      // this.uploadService.upload(this.currentFile).subscribe(
      //   (event: any) => {
      //     if (event.type === HttpEventType.UploadProgress) {
      //       this.progress = Math.round(100 * event.loaded / event.total);
      //     } else if (event instanceof HttpResponse) {
      //       this.message = event.body.message;
      //       this.fileInfos = this.uploadService.getFiles();
      //     }
      //   },
      //   (err: any) => {
      //     console.log(err);
      //     this.progress = 0;

      //     if (err.error && err.error.message) {
      //       this.message = err.error.message;
      //     } else {
      //       this.message = 'Could not upload the file!';
      //     }

      //     this.currentFile = undefined;
      //   });
    }

  }
}
