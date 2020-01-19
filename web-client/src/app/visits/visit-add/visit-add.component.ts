import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-visit-add',
  templateUrl: './visit-add.component.html',
  styleUrls: ['./visit-add.component.css']
})
export class VisitAddComponent implements OnInit {

  submitted = false;

  private addVisitForm = new FormGroup({

    });

  constructor(private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.addVisitForm = this.formBuilder.group({
      patientId: [''],
      doctorId: [''],
      firstNamePatient: [''],
      lastNamePatient: [''],
      firstNameDoctor: [''],
      lastNameDoctor: [''],
      date: [''],
      time: [''],
    });
  }
  get f() { return this.addVisitForm.controls; }

  addVisitToDB() {
    this.submitted = true;

    alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.addVisitForm.value));

    // stop here if form is invalid
    if (this.addVisitForm.invalid) {
      return;
    }
  }


  returnToVisitList(){
    this.router.navigate(['/visitlist/'])
  }

}
