import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Visit} from "../data/visits/visit";

@Injectable({
  providedIn: 'root'
})
export class VisitsService {

  constructor(private http: HttpClient) { }

  getVisits(name, surname): Observable<Visit[]>{
    //zmienic url aby mozna bylo wyszukiwac wizyty osob o okonkretnych nazwiskach
    return this.http.get<Visit[]>('http://localhost:8080/api/visits/');
  }

  getDoctorsVisits(userId): Observable<Visit[]>{
    return this.http.get<Visit[]>('http://localhost:8080/api/doctors/?user_id='+userId+'/visits');
  }

  getPatientsVisits(userId): Observable<Visit[]>{
    return this.http.get<Visit[]>('http://localhost:8080/api/patients/?user_id='+userId+'/visits');
  }

  getSpecificVisit(visitId): Observable<Visit>{
    return this.http.get<Visit>('http://localhost:8080/api/visits/?visit_id='+visitId);
  }

  getVisitDocuments(visitId): Observable<Document[]>{
    return this.http.get<Document[]>('http://localhost:8080/api/visits/?visit_id='+visitId+'/documents');
  }

  getVisitServices(visitId): Observable<VisitsService[]>{
    return this.http.get<VisitsService[]>('http://localhost:8080/api/visits/?visit_id='+visitId+'/services');
  }
}
