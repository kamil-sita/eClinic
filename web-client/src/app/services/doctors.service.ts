import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Doctor} from '../data/doctors/doctor';
import {tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DoctorsService {

  constructor(private http: HttpClient) {
  }

  /*getDoctorList(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>('http://localhost:8080/api/employees/doctors');
  }*/

  getDoctorList(name, surname): Observable<Doctor[]> {
    return this.http.get<Doctor[]>('http://localhost:8080/api/employees/doctors/?first_name='+name+'&last_name='+surname);
  }
}
