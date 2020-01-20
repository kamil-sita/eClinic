import { Component, OnInit } from '@angular/core';
import {Patient} from '../../data/patients/patient';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {PatientsService} from '../../services/patients.service';
import {VisitAddComponent} from "../../visits/visit-add/visit-add.component";
import {VisitToAdd} from "../../data/visits/visit-to-add";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  patient:Patient;
  userId;

  constructor(private patientsService: PatientsService, private route: ActivatedRoute, private location: Location, private router: Router) { }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('id');
    this.patientsService.getPatient(this.userId).subscribe(pat => this.patient = pat);
  }

  backClicked() {
    this.location.back();
  }

  toIllnessHistory(userId: number) {
    this.router.navigate(['/patientlist/'+userId+'/history'])
  }

  toVisitAdd(userId: number) {
    VisitToAdd.patientId = userId;
    this.router.navigate(['/doctorlist/'])
  }
}
