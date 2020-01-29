import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Employee} from '../data/doctors/doctor';
import {DaySchedule} from '../data/schedule/day-schedule';

@Injectable({
  providedIn: 'root'
})
export class DoctorsService {

  constructor(private http: HttpClient) {
  }

  getDoctorList(name, surname): Observable<Employee[]> {
    return this.http.get<Employee[]>('http://localhost:8080/api/employees/doctors/?first_name='+name+'&last_name='+surname);
  }

  //zwraca listę z jednym doktorkiem
  getDoctor(userId): Observable<Employee[]>{
    return this.http.get<Employee[]>('http://localhost:8080/api/employees/doctors/?user_id='+userId);
  }

  getDoctorSchedule(userId): Observable<DaySchedule[]>{
    return this.http.get<DaySchedule[]>('http://localhost:8080/api/employees/'+userId+'/schedule');
  }
}
