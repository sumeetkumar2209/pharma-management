import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Title } from '@angular/platform-browser';
import { NGXLogger } from 'ngx-logger';
import { AuthenticationService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-dashboard-home',
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.css']
})
export class DashboardHomeComponent implements OnInit {
  currentUser: any;
  gridColumns = 3;
  cards = [{
    title: 'Supplier Summary',
    lines:[
      {label: 'Total Suppliers', count: 100},
      {label: 'Qualified Suppliers', count: 100},
      {label: 'Non-Qualified Suppliers', count: 100}
    ]
  },
  {
    title: 'Customer Summary',
    lines:[
      {label: 'Total Customer', count: 100},
      {label: 'Qualified Customer', count: 100},
      {label: 'Non-Qualified Customer', count: 100}
    ]
  },
  {
    title: 'Warehouse Location',
    lines:[
      {label: 'Location occupied(no. of shells)', count: 100},
      {label: 'Location Available', count: 100},
      {label: '%Warehouse occupancy', count: 100}
    ]
  },
  {
    title: 'Stock Summary',
    lines:[
      {label: 'Batches in Quarantine', count: 100},
      {label: 'Batches in Testing', count: 100},
      {label: 'Batches Released', count: 100},
      {label: 'Batches Rejected', count: 100},
      {label: 'Batches Returned', count: 100}
    ]
  },
  {
    title: 'Purchase Summary',
    lines:[
      {label: 'Purchase order generated', count: 100},
      {label: 'Purchase order closed', count: 100},
      {label: 'Purchase order in-progress', count: 100},
      {label: 'Purchase order cancelled', count: 100}
    ]
  },
  {
    title: 'Order Summary',
    lines:[
      {label: 'Order created', count: 100},
      {label: 'Order picked', count: 100},
      {label: 'Order packed', count: 100},
      {label: 'Order dispactched', count: 100},
      {label: 'Order cancelled', count: 100},
      {label: 'Order pending', count: 100}
     
    ]
  }
]

  constructor(private notificationService: NotificationService,
    private authService: AuthenticationService,
    private titleService: Title,
    private logger: NGXLogger) {
  }

  ngOnInit() {
  
    this.currentUser = this.authService.getCurrentUser();
    this.titleService.setTitle('Reiphy Pharma - Dashboard');
    this.logger.log('Dashboard loaded');

    setTimeout(() => {
      this.notificationService.openSnackBar('Welcome!');
    });
  }
}
