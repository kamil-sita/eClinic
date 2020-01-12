import { Component, OnInit } from '@angular/core';
import {Doctor} from '../../data/doctors/doctor';
import {DoctorsService} from '../../services/doctors.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css']
})
export class DoctorDetailsComponent implements OnInit {

  doctor:Doctor;
  userId;

  constructor(private doctorsService: DoctorsService, private route: ActivatedRoute, private location: Location, private router: Router) { }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('id');
    this.doctorsService.getDoctor(this.userId).subscribe(doc => this.doctor = doc[0]);
  }

  backClicked() {
    this.location.back();
  }

  toSchedule(userId) {
    this.router.navigate(['/doctorlist/'+userId+'/schedule'])
  }
}
