import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {MedicalService} from "../data/services/medical-service";
import {Visit} from "../data/visits/visit";

@Injectable({
  providedIn: 'root'
})
export class MedicalServiceService {

  constructor(private http: HttpClient) { }

  getMedicalServiceList(): Observable<MedicalService[]> {
    return this.http.get<MedicalService[]>('http://localhost:8080/api/medical_services');
  }
}
