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
  selector: 'app-add-supplier-page',
  templateUrl: './add-supplier-page.component.html',
  styleUrls: ['./add-supplier-page.component.css']
})
export class AddSupplierPageComponent {
  addSupplierForm: FormGroup;
  supplier: any;
  countryList:String[]=['IN','UK','USA'];
  currencyList:String[]=['INR','USD','GBP'];
  statusList:String[]=['Active','Inactive'];
  qualifyList:String[]=['QUALIFIED','NOT-QUALIFIED'];
  approverList:String[]=['Vijay','Prakash'];


  constructor(private fb: FormBuilder, private titleService: Title,
    private logger: NGXLogger,private notificationService: NotificationService) {
    this.addSupplierForm = this.fb.group({
    
      'companyName': new FormControl(null, Validators.required),
      'contactName': new FormControl(null, Validators.required),
      'contactEmail': new FormControl(null, [Validators.required, Validators.email]),
      'contactNumber': new FormControl(null, Validators.required),
      'contactCountry': new FormControl(null, Validators.required),
      'currency': new FormControl(null, Validators.required),
      'status': new FormControl(null, Validators.required),
      'qualification': new FormControl(null, Validators.required),
      'validTill': new FormControl(null, Validators.required),
      'address1': new FormControl(null),
      'address2': new FormControl(null),
      'address3': new FormControl(null),
      'town': new FormControl(null, Validators.required),
      'postcode': new FormControl(null, Validators.required),
      'country': new FormControl(null, Validators.required),
      'approver': new FormControl(null, Validators.required),
     
    });

  }
  ngOnInit() {
  
    this.titleService.setTitle('Reiphy Pharma - Add Supplier');
    this.logger.log('Add Supplier loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Fill in the form to Add Supplier!');
    });
  }
  onSubmit(post: any) {
    this.supplier = post;
  }

  get apControls(): { [key: string]: AbstractControl } {
    return this.addSupplierForm.controls;
  }

  getError(controlName: string) {
    return this.apControls[controlName].hasError('required') ? 'Field is required' :
      this.apControls[controlName].hasError('pattern') ? 'Not a valid pattern' :
        '';
  }

  save(){}

}
