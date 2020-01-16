import { Component, OnInit } from '@angular/core';
import {Employee} from '../../data/doctors/doctor';
import {DoctorsService} from '../../services/doctors.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent implements OnInit {

  doctorList : Employee[];
  name: string = '';
  surname: string = '';



  constructor(private doctorsService: DoctorsService, private router: Router) {
  }

  ngOnInit() {
    this.doctorsService.getDoctorList(this.name, this.surname).subscribe(doctor => this.doctorList = doctor);
  }

  toDoctorDetails(doctorId) {
    this.router.navigate(['/doctorlist/'+doctorId]);
  }


}
