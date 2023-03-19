import { Component, Inject } from '@angular/core';
import { AuthenticationService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  template: `<router-outlet></router-outlet>`
})
export class AppComponent {
  constructor(private authService:AuthenticationService,
    @Inject('LOCALSTORAGE') private localStorage: Storage) {
      const localUser = this.localStorage.getItem('currentUser');
    if (localUser && !this.authService.getCurrentUserDetails()){
      this.authService.fetchUserDetails();
    }
  }
}
