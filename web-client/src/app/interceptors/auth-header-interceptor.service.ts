import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {UserDetails} from "../data/users/user-details";
import {UserService} from "../services/user.service";

@Injectable()
export class AuthHeaderInterceptor implements HttpInterceptor {

  constructor(private userService: UserService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let copiedReq;
    if(this.userService.hasUser()) {
      copiedReq = request.clone({
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': 'Basic ' + this.userService.credentials,
        })
      });
    }else{
      copiedReq = request.clone();
    }

    return next.handle(copiedReq);
  }

}
