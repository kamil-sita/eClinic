import { Component, OnInit } from '@angular/core';
import {DoctorsService} from '../../services/doctors.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DaySchedule} from '../../data/schedule/day-schedule';
import {Employee} from '../../data/doctors/doctor';
import {Location} from '@angular/common';

@Component({
  selector: 'app-doctor-schedule',
  templateUrl: './doctor-schedule.component.html',
  styleUrls: ['./doctor-schedule.component.css']
})
export class DoctorScheduleComponent implements OnInit {

  userId;
  weekSchedule:DaySchedule[];
  doctor: Employee;

  constructor(private doctorsService: DoctorsService, private route: ActivatedRoute, private location: Location, private router: Router) { }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('id');
    this.doctorsService.getDoctorSchedule(this.userId).subscribe(wS => this.weekSchedule = wS);
    this.doctorsService.getDoctor(this.userId).subscribe(doc => this.doctor = doc[0]);
  }

  backClicked() {
    this.location.back();
  }

  toScheduleEdit(userId: number) {
    this.router.navigate(['/doctorlist/'+userId+'schedule/edit'])
  }
}
