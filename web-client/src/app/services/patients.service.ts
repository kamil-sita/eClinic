import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Document} from '../data/documents/document';
import {Patient} from '../data/patients/patient';
import {History} from '../data/documents/history';

@Injectable({
  providedIn: 'root'
})
export class PatientsService {

  constructor(private http: HttpClient) { }

  getPatientsList(name, surname): Observable<Patient[]> {
    return this.http.get<Patient[]>('http://localhost:8080/api/patients/?first_name='+name+'&last_name='+surname);
  }

  //zwraca listę z jednym pacjentem
  getPatient(userId): Observable<Patient>{
    return this.http.get<Patient>('http://localhost:8080/api/patients/'+userId);
  }

  getPatientHistory(userId): Observable<History>{
    return this.http.get<History>('http://localhost:8080/api/patients/'+userId+'/history');
  }
}
