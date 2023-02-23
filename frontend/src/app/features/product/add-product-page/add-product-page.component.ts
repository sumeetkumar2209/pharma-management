import { Component, ViewChild } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { NotificationService } from 'src/app/core/services/notification.service';


@Component({
  selector: 'app-add-product-page',
  templateUrl: './add-product-page.component.html',
  styleUrls: ['./add-product-page.component.css']
})
export class AddProductPageComponent {
  addProductForm: FormGroup;
  product: any;


  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger,private notificationService: NotificationService) {
    this.addProductForm = this.fb.group({
      'client': new FormControl(null, Validators.required),
      'sku': new FormControl(null, Validators.required),
      'ean_barcode': new FormControl(null, Validators.required),
      'upc_barcode': new FormControl(null, Validators.required),
      'name': new FormControl(null, Validators.required),
      'description': new FormControl(null, Validators.required),
      'customDescription': new FormControl(null, Validators.required),
      'countryManufacture': new FormControl(null, Validators.required),
      'commodityCode': new FormControl(null, Validators.required),
      'discontinued': new FormControl(null, Validators.required),
      'preorderable': new FormControl(null, Validators.required),
      'imageUrl': new FormControl(null, Validators.required),
    });

  }
  ngOnInit() {
  
    this.titleService.setTitle('Reiphy Pharma - Add Product');
    this.logger.log('Add Product loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Fill in the form to Add Product!');
    });
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


}
