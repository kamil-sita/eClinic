import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler, HttpHeaderResponse,
  HttpInterceptor, HttpProgressEvent,
  HttpRequest,
  HttpResponse, HttpSentEvent, HttpUserEvent
} from "@angular/common/http";
import {UserService} from "../services/user.service";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";

@Injectable()
export class HttpUnauthorizedInterceptor implements HttpInterceptor {

  constructor(public userService: UserService, private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).pipe(tap(() => {
      },
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            return;
          }
          this.userService.failRecentLogin();
          this.router.navigate(['loginpage']);
        }
      }));
  }
}
