import {Injectable} from '@angular/core';
import {UserDetails} from "../data/users/user-details";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserCredentials} from "../data/users/user-credentials";
import {Observable, Subject} from "rxjs";
import {UserRole} from "../data/users/user-role.enum";
import {map, tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _user: UserDetails;
  private subject = new Subject<any>();
  private _credentials: string;


  constructor(private httpClient: HttpClient) {
  }

  sigIn(credentials: UserCredentials): Observable<UserDetails> {

    const encodedCredentials = btoa(credentials.username + ':' + credentials.password);
    this._credentials = encodedCredentials;
    return this.httpClient
      .get<UserDetails>('http://localhost:8080/api/auth/userinfo', {
        headers: {'Authorization': 'Basic ' + encodedCredentials}
      })
      .pipe(tap(user => this.sendUserName(user.firstName + ' ' + user.lastName)))
      .pipe(tap(user => this._user = user))

  }

  isDoctor(): boolean {
    return UserRole.DOCTOR === this._user.role;
  }


  isPatient(): boolean {
    return UserRole.PATIENT === this._user.role;
  }

  isReceptionist(): boolean {
    return UserRole.RECEPTIONIST === this._user.role;
  }


  isEmployee(): boolean {
    return this.isDoctor() || this.isReceptionist();
  }

  hasUser(): boolean {
    return this._user != null;
  }

  get user(): UserDetails {
    return this._user;
  }

  sendUserName(username: string) {
    this.subject.next(username);
  }

  getObservableUserName(): Observable<any> {
    return this.subject.asObservable();
  }

  get credentials(): string {
    return this._credentials;
  }
}
