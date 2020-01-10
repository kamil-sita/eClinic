import { Component, OnInit } from '@angular/core';
import {DaySchedule} from '../../data/schedule/day-schedule';
import {Doctor} from '../../data/doctors/doctor';
import {Document} from '../../data/documents/document';
import {Patient} from '../../data/patients/patient';
import {DoctorsService} from '../../services/doctors.service';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {PatientsService} from '../../services/patients.service';

@Component({
  selector: 'app-illness-history',
  templateUrl: './illness-history.component.html',
  styleUrls: ['./illness-history.component.css']
})
export class IllnessHistoryComponent implements OnInit {

  userId;
  history:Document[];
  patient:Patient;

  constructor(private patientsService: PatientsService, private route: ActivatedRoute, private location: Location) { }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('id');
    this.patientsService.getPatientHistory(this.userId).subscribe(his => this.history = his.history);
    this.patientsService.getPatient(this.userId).subscribe(pat => this.patient = pat);
  }

  backClicked() {
    this.location.back();
  }

}
