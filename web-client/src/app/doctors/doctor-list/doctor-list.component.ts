import { Component, OnInit } from '@angular/core';
import {Doctor} from '../../data/doctors/doctor';
import {DoctorListService} from '../../services/doctor-list.service';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent implements OnInit {

  doctorList : Doctor[];

  constructor(private doctorListService: DoctorListService) { }

  ngOnInit() {
    this.doctorListService.getDoctorList().subscribe(doctor => this.doctorList = doctor);
  }

}
