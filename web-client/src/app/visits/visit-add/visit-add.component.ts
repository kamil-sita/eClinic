import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-visit-add',
  templateUrl: './visit-add.component.html',
  styleUrls: ['./visit-add.component.css']
})
export class VisitAddComponent implements OnInit {

  private addVisitForm = new FormGroup({

    });

  constructor() { }

  ngOnInit() {
  }

}
