import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  private username: string;

  constructor(private userService: UserService) { }

  ngOnInit() {
    if(this.userService.hasUser())
      this.username = this.userService.user.firstName + ' ' +this.userService.user.lastName;
  }

  usernamePresent(): boolean{
    return this.username != null;
  }

}
