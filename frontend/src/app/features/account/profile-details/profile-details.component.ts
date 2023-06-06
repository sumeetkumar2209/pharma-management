import { Component, OnInit } from '@angular/core';
import { UserDetails } from 'src/app/core/classes/user-details';
import { AuthenticationService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css']
})
export class ProfileDetailsComponent implements OnInit {

  fullName: string = "";
  email: string = "";
  alias: string = "";

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    this.authService.currentUserDetails.subscribe((user:UserDetails) => {
      this.fullName = user.fullName;
      this.email = user.email;
    })
  }

}
