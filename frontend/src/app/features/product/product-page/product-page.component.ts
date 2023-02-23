import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Title } from '@angular/platform-browser';
import { NotificationService } from 'src/app/core/services/notification.service';
import { NGXLogger } from 'ngx-logger';


@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = ['client', 'name', 'code', 'conatactName','conatactNumber',
  'conatactEmail',
  'currency',
  'active',
  'lastUpdated',
  'lastUpdatedBy',
  'actions'];


   
  dataToDisplay = [...ELEMENT_DATA];

  dataSource = new MatTableDataSource(this.dataToDisplay);
  constructor(private titleService: Title,
    private logger: NGXLogger,private notificationService: NotificationService) { }
  ngAfterViewInit() {
    this.titleService.setTitle('Reiphy Pharma - Suppliers');
    this.logger.log('Supplier loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Supplier section loaded!');
    });
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  addData() {
    const randomElementIndex = Math.floor(Math.random() * ELEMENT_DATA.length);
    this.dataToDisplay = [...this.dataToDisplay, ELEMENT_DATA[randomElementIndex]];
    // this.dataSource.setData(this.dataToDisplay);
  }

  removeData() {
    this.dataToDisplay = this.dataToDisplay.slice(0, -1);
    // this.dataSource.setData(this.dataToDisplay);
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    if (filterValue !== ''){
      this.dataSource.filter = filterValue.trim().toLowerCase();
      if (this.dataSource.paginator) {
        this.dataSource.paginator.firstPage();
      }
    }
  }

}

export interface Supplier {
  client: string;
  name: string;
  code: number;
  conatactName: string;
  conatactNumber: string;
  conatactEmail: string;
  currency: string;
  active: string;
  lastUpdated: string;
  lastUpdatedBy: string;
  actions: string;
}

const ELEMENT_DATA: Supplier[] = [
  {
    client: 'Reiphy Pharma Ltd.',
    name:'MEDLEY PHARMACEUTICAL LTD',
    code:101,
    conatactName: 'Vikas',
    conatactNumber: '9999999',
    conatactEmail: 'v@yahoo.com',
    currency: 'GBP',
    active: 'Y',
    lastUpdated: '',
    lastUpdatedBy: '',
    actions: 'pick'
  },
  {
    client: 'Reiphi Pharma Ltd.',
    name:'RICHI PHARMACEUTICAL LTD',
    code:101,
    conatactName: 'Vikas',
    conatactNumber: '9999999',
    conatactEmail: 'v@yahoo.com',
    currency: 'GBP',
    active: 'Y',
    lastUpdated: '',
    lastUpdatedBy: '',
    actions: 'pick'
  },
 ];

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}


class ExampleDataSource extends MatTableDataSource<Supplier> {
  private _dataStream = new ReplaySubject<Supplier[]>();

  constructor(initialData: Supplier[]) {
    super();
    this.setData(initialData);
  }

  // connect(): Observable<Supplier[]> {
  //   return this._dataStream;
  // }

  // disconnect() {}

  setData(data: Supplier[]) {
    this._dataStream.next(data);
  }
}

