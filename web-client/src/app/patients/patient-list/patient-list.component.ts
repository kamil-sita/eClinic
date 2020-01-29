import { Component, OnInit } from '@angular/core';
import {PatientsService} from '../../services/patients.service';
import {Patient} from '../../data/patients/patient';
import {Router} from '@angular/router';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  patientList : Patient[];
  name: string = '';
  surname: string = '';

  constructor(private patientsService: PatientsService, private router: Router) { }

  ngOnInit() {
    this.patientsService.getPatientsList(this.name, this.surname).subscribe(pat => this.patientList = pat);
  }

  toPatientDetails(userId: number) {
    this.router.navigate(['patientlist/'+userId]);
  }
}
