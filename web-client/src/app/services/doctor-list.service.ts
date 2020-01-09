import { Injectable } from '@angular/core';
import {Doctor} from '../data/doctors/doctor';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DoctorListService {

  private URL: string;

  constructor(private http: HttpClient) {
    this.URL = 'http://localhost:8080/api/employees/doctors';
  }

  getDoctorList(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.URL).pipe(tap(data => console.log('All' + JSON.stringify(data))));;
  }

}
