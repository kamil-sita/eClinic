import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit  {

  private username: string;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
  }

  usernamePresent(): boolean {
    if (this.userService.hasUser())
      this.username = this.userService.user.firstName + ' ' + this.userService.user.lastName;
    else
      this.username = null;
    return this.username != null;
  }

}
