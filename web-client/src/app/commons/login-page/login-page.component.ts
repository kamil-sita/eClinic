import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {UserDetails} from "../../data/users/user-details";
import {UserCredentials} from "../../data/users/user-credentials";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit, OnDestroy {

  private credentialsForm = new FormGroup({
    username: new FormControl(),
    password: new FormControl()
  });

  userSub: Subscription;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
  }

  signIn() {
    const credentials = new UserCredentials(this.credentialsForm.value.username, this.credentialsForm.value.password);

    this.userService.sigIn(credentials).subscribe(
      userDetails => {
        console.log(userDetails)
      },
      error => {
        window.alert(error)
      }
    )
  }

  ngOnDestroy() {
    if (this.userSub != null)
      this.userSub.unsubscribe()
  }

}
