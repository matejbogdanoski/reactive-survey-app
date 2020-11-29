import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { select, Store } from '@ngrx/store';
import { Observable, of } from 'rxjs';
import { first, mergeMap } from 'rxjs/operators';
import { SecurityState } from '../store/security.state';
import { selectToken } from '../store/security.selectors';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    private store$: Store<SecurityState>
  ) {
  }

  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const isAuthEndpoint = request.url.includes('api/auth') || request.url.includes('api/users/signup') || request.url.includes('assets');
    if (!isAuthEndpoint) {
      return this.addToken(request).pipe(
        first(),
        mergeMap((requestWithToken: HttpRequest<any>) => next.handle(requestWithToken))
      );
    } else {
      return next.handle(request);
    }
  }

  private addToken(request: HttpRequest<any>): Observable<HttpRequest<any>> {
    return this.store$.pipe(
      select(selectToken),
      first(),
      mergeMap((token: string) => {
        if (!!token) {
          request = request.clone({
            headers: request.headers.set('Authorization', `Bearer ${token}`),
            withCredentials: true
          });
        } else {
          console.warn(`addToken( Invalid token!!! Cannot use token "${token}" for endpoint: ${request.url} ).`);
        }
        return of(request);
      })
    );
  }

}
