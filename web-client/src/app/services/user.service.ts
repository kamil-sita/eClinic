import {Injectable} from '@angular/core';
import {UserDetails} from "../data/users/user-details";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserCredentials} from "../data/users/user-credentials";
import {Observable} from "rxjs";
import {UserRole} from "../data/users/user-role.enum";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _user: UserDetails;

  constructor(private httpClient: HttpClient) {
  }

  sigIn(credentials: UserCredentials): Observable<UserDetails> {

    const encodedCredentials = btoa(credentials.username + ':' + credentials.password);
    return this.httpClient.get<UserDetails>('http://localhost:8080/api/auth/userinfo',
      {
        headers: {'Authorization': 'Basic ' + encodedCredentials}
      });
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

  get user(): UserDetails {
    return this._user;
  }


}
