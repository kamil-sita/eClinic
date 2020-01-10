import { Component, OnInit } from '@angular/core';
import {Doctor} from '../../data/doctors/doctor';
import {DoctorsService} from '../../services/doctors.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css']
})
export class DoctorDetailsComponent implements OnInit {

  doctor:Doctor;
  userId;

  constructor(private doctorsService: DoctorsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('id');
    this.doctorsService.getDoctor(this.userId).subscribe(doc => this.doctor = doc[0]);
  }

}
