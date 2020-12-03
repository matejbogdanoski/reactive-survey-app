import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticationRequest } from '../../interfaces/request/authentication.request';
import { Observable, of } from 'rxjs';
import { AuthenticationResponse } from '../../interfaces/response/authentication.response';
import { CookieService } from 'ngx-cookie-service';
import { tap } from 'rxjs/operators';

@Injectable()
export class AuthenticationService {

  private readonly path = `api/auth`;

  constructor(
    private _http: HttpClient,
    private _cookie: CookieService
  ) { }

  public login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this._http.post<AuthenticationResponse>(`${this.path}`, request, { withCredentials: true }).pipe(
      tap(response => {
        this._cookie.set('JWT_TOKEN', response.token, this.getTomorrow(), '/');
        this._cookie.set('USER', response.username, this.getTomorrow(), '/');
      })
    );
  }

  public logout(): Observable<any> {
    this._cookie.delete('JWT_TOKEN', '/');
    this._cookie.delete('USER', '/');
    return of({});
  }

  private getTomorrow(): Date {
    let tomorrow = new Date();
    tomorrow.setDate(new Date().getDate() + 1);
    return tomorrow;
  }

  getUserFromCookies(): Observable<{ username: string, token: string }> {
    const username = this._cookie.get('USER');
    const token = this._cookie.get('JWT_TOKEN');
    return of({ username, token });
  }
}
