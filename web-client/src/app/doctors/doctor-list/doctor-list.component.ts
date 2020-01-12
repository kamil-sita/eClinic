import { Component, OnInit } from '@angular/core';
import {Doctor} from '../../data/doctors/doctor';
import {DoctorsService} from '../../services/doctors.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent implements OnInit {

  doctorList : Doctor[];
  name: string = '';
  surname: string = '';

  constructor(private doctorsService: DoctorsService, private router: Router) {
  }

  ngOnInit() {
    this.doctorsService.getDoctorList(this.name, this.surname).subscribe(doctor => this.doctorList = doctor);
  }

  navigateToDetails(doctorid){
    this.router.navigate(['doctorlist/' + doctorid])
  }

}
