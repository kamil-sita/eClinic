import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Doctor} from '../data/doctors/doctor';
import {tap} from 'rxjs/operators';
import {DaySchedule} from '../data/schedule/day-schedule';

@Injectable({
  providedIn: 'root'
})
export class DoctorsService {

  constructor(private http: HttpClient) {
  }

  getDoctorList(name, surname): Observable<Doctor[]> {
    return this.http.get<Doctor[]>('http://localhost:8080/api/employees/doctors/?first_name='+name+'&last_name='+surname);
  }

  //zwraca listę z jednym doktorkiem
  getDoctor(userId): Observable<Doctor[]>{
    return this.http.get<Doctor[]>('http://localhost:8080/api/employees/doctors/?user_id='+userId);
  }

  getDoctorSchedule(userId): Observable<DaySchedule[]>{
    return this.http.get<DaySchedule[]>('http://localhost:8080/api/employees/'+userId+'/schedule');
  }
}
