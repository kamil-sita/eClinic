import {Component, OnDestroy} from '@angular/core';
import {UserService} from "./services/user.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
  title = 'web-client';

  private subscription: Subscription;
  private username: string;

  constructor(private userService: UserService, private router: Router) {
    this.subscription = this.userService.getObservableUserName().subscribe(
      username => {
        this.username = username;
      }
    );
  }

  usernamePresent(): boolean{
    return this.username != null;
  }

  naviateToLoginPage(){
    this.router.navigate(['loginpage']);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }


}
