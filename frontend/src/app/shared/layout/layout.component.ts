import { Component, OnInit, ChangeDetectorRef, OnDestroy, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { timer } from 'rxjs';
import { Subscription } from 'rxjs';

 import { AuthenticationService } from 'src/app/core/services/auth.service';
import { SpinnerService } from '../../core/services/spinner.service';
import { AuthGuard } from 'src/app/core/guards/auth.guard';

@Component({
    selector: 'app-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class LayoutComponent implements OnInit, OnDestroy, AfterViewInit {

    private _mobileQueryListener: () => void;
    mobileQuery: MediaQueryList;
    showSpinner: boolean = false;
    userName: string = "";
    isAdmin: boolean = false;

    notificationItems = [];
    sideNavItems = JSON.parse('[{"label":"Dashboard","icon":"dashboard","link":["/dashboard"],"order":0,"subitem":[]},{"label":"Supplier","order":1,"icon":"flare","link":["/supplier"],"subitem":[]},{"label":"Customer","order":1,"icon":"phonelink","link":["/customer"],"subitem":[]},{"label":"Transport","order":2,"icon":"local_shipping","link":["/transport"],"subitem":[]},{"label":"Product","icon":"view_module","order":3,"link":["/product/add"],"subitem":[]},{"label":"Purchase Orders","order":4,"link":["/purchase-order"],"icon":"account_balance_wallet","subitem":[]},{"label":"Warehouse","order":5,"icon":"home","link":["/warehouse"],"subitem":[]},{"label":"Customer Orders","order":6,"icon":"shopping_cart","link":["/orders"],"subitem":[]},{"label":"Goods Returned","order":4,"icon":"compare_arrows","link":["/goods"],"subitem":[]},{"label":"Reports","order":3,"icon":"next_week","link":["/reports"],"subitem":[]},{"label":"Audit Trail","order":3,"icon":"account_balance","link":["/audit"],"subitem":[]}]'

    );
    private autoLogoutSubscription: Subscription = new Subscription;

    constructor(private changeDetectorRef: ChangeDetectorRef,
        private media: MediaMatcher,
        public spinnerService: SpinnerService,
        private authService: AuthenticationService,
        private authGuard: AuthGuard) {

        this.mobileQuery = this.media.matchMedia('(max-width: 1000px)');
        this._mobileQueryListener = () => changeDetectorRef.detectChanges();
        // tslint:disable-next-line: deprecation
        this.mobileQuery.addListener(this._mobileQueryListener);
    }

    ngOnInit(): void {
        const user = this.authService.getCurrentUser();

        this.isAdmin = user.isAdmin;
        this.userName = user.fullName;

        // Auto log-out subscription
        const timer$ = timer(2000, 5000);
        this.autoLogoutSubscription = timer$.subscribe(() => {
            this.authGuard.canActivate();
        });
    }

    ngOnDestroy(): void {
        // tslint:disable-next-line: deprecation
        this.mobileQuery.removeListener(this._mobileQueryListener);
        this.autoLogoutSubscription.unsubscribe();
    }

    ngAfterViewInit(): void {
        this.changeDetectorRef.detectChanges();
    }
}
