import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {UserDetails} from "../../data/users/user-details";
import {UserRole} from "../../data/users/user-role.enum";

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {

  private userDetails: UserDetails;

  constructor(private userService: UserService) { }

  ngOnInit() {
    if(this.userService.hasUser())
      this.userDetails = this.userService.user;
  }

  translateRole(): string{

    if(this.userService.isReceptionist())
      return "Recepcjonista";

    if(this.userService.isDoctor())
      return "Lekarz";

    return "Konto użytkownika"
  }

}
