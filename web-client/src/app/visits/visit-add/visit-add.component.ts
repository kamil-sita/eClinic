import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {Patient} from "../../data/patients/patient";
import {VisitToAdd} from "../../data/visits/visit-to-add";
import {VisitsService} from "../../services/visits.service";
import {CreatorState} from "../creator-state.enum";
import {PatientsService} from "../../services/patients.service";
import {MedicalServiceService} from "../../services/medical-service.service";
import {MedicalService} from "../../data/services/medical-service";
import {Employee} from "../../data/doctors/doctor";
import {DoctorsService} from "../../services/doctors.service";

@Component({
  selector: 'app-visit-add',
  templateUrl: './visit-add.component.html',
  styleUrls: ['./visit-add.component.css']
})
export class VisitAddComponent implements OnInit {

  submitted = false;

  state: CreatorState = CreatorState.PATIENT_SELECTION;
  patientList: Patient[];
  serviceList: MedicalService[];
  doctorList: Employee[];


  patientId: number;
  employeeId: number;
  serviceId: number;
  date: string;
  hour: string;

  visit: VisitToAdd;

  constructor(private patientsService: PatientsService,
              private medicalService: MedicalServiceService,
              private doctorsService: DoctorsService,
              private formBuilder: FormBuilder,
              private router: Router,
              private visitsService: VisitsService,
  ) { }

  ngOnInit() {
    this.patientsService.getPatientsList('', '').subscribe(pat => this.patientList = pat);
    this.medicalService.getMedicalServiceList().subscribe(srv => this.serviceList = srv);
    this.doctorsService.getDoctorList('', '').subscribe(doc => this.doctorList = doc);

  }


  saveVisit(){
    this.visit = new VisitToAdd(
      this.patientId,
      this.serviceId,
      this.employeeId,
      this.date,
      this.hour);


    this.visitsService.postVisit(this.visit).subscribe(next => console.log(next));
  }

  isStatePatientSelection(){
    return this.state === CreatorState.PATIENT_SELECTION;
  }

  isStateServiceSelection(){
    return this.state === CreatorState.SERVICE_SELECTION
  }

  isStateDoctorSelection(){
    return this.state === CreatorState.DOCTOR_SELECTION
  }

  isStateDateSelection(){
    return this.state === CreatorState.DATE_SELECTION;
  }

  isStateEndSelection(){
    return this.state === CreatorState.END_SELECTION;
  }

  moveToServiceSelection(patientId: number){
    this.patientId = patientId;
    console.log(this.patientId);
    this.state = CreatorState.SERVICE_SELECTION;
  }

  movetoDoctorSelection(medicalServiceId: number) {
    this.serviceId = medicalServiceId;
    console.log(this.serviceId);
    this.state = CreatorState.DOCTOR_SELECTION;
  }

  movetoDateTimeSelection(employeeId: number){
    this.employeeId = employeeId;
    console.log(this.employeeId);
    this.state = CreatorState.DATE_SELECTION;
  }

  movetoEndSelection(date: string, hour: string){
    this.date = date;
    console.log(typeof this.date);

    //To change data to correct type required by database
    this.hour = hour + ":00";
    console.log(typeof this.hour);

    // Pass data to post() method:
    this.saveVisit();

    this.state = CreatorState.END_SELECTION;
  }

  goToHomepage(){
    this.router.navigate(['/homepage/']);
  }
}
