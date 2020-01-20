import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import { FormBuilder } from '@angular/forms';
import {Visit} from "../../data/visits/visit";
import {Employee} from "../../data/doctors/doctor";
import {Patient} from "../../data/patients/patient";
import {VisitToAdd} from "../../data/visits/visit-to-add";
import {VisitsService} from "../../services/visits.service";

@Component({
  selector: 'app-visit-add',
  templateUrl: './visit-add.component.html',
  styleUrls: ['./visit-add.component.css']
})
export class VisitAddComponent implements OnInit {

  submitted = false;
  visit: VisitToAdd;
  employee: Employee;
  patient: Patient;

  ///jeszcze data i godzina

  private addVisitForm = new FormGroup({

    });

  constructor(private visitsService: VisitsService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.addVisitForm = this.formBuilder.group({
      patientId: [''],
      serviceId: [''],
      employeeId: [''],
      scheduledDate: [''],
      startingTime: [''],

    });
  }
  get f() { return this.addVisitForm.controls; }

  assignPatient(patient: Patient){
    this.patient = patient;
    //this.visit.patient = this.patient;
  }

  addVisitToDB() {
    this.visitsService.postVisit(new VisitToAdd(
      this.patient.userId,
      this.visit.serviceId,
      this.employee.user.userId,
      this.visit.scheduledDate,
      this.visit.startingTime
      ));

    this.submitted = true;

    alert('Wprowadzone dane: \n\n' + JSON.stringify(this.addVisitForm.value));

    // stop here if form is invalid
    if (this.addVisitForm.invalid) {
      return;
    }
  }



  returnToVisitList(){
    this.router.navigate(['/visitlist/'])
  }

}
