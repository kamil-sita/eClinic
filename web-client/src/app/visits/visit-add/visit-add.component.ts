import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-visit-add',
  templateUrl: './visit-add.component.html',
  styleUrls: ['./visit-add.component.css']
})
export class VisitAddComponent implements OnInit {

  private addVisitForm = new FormGroup({

    });

  constructor(private router: Router) { }

  ngOnInit() {
  }

  addVisitToDB(){
    //Add visit
  }

  returnToVisitList(){
    this.router.navigate(['/visitlist/'])
  }

}
