import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Visit} from "../data/visits/visit";
import {VisitToAdd} from "../data/visits/visit-to-add";

@Injectable({
  providedIn: 'root'
})
export class VisitsService {

  constructor(private http: HttpClient) {
  }

  getVisits(): Observable<Visit[]> {
    return this.http.get<Visit[]>('http://localhost:8080/api/visits/');
  }

  getVisit(visitId): Observable<Visit> {
    return this.http.get<Visit>('http://localhost:8080/api/visits/' + visitId);
  }

  postVisit(visit: VisitToAdd): Observable<Visit> {
    console.log(visit);

    return this.http.post<Visit>("http://localhost:8080/api/visits", visit);
  }
}
