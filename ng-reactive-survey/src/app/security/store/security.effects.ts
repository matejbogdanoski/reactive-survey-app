import { Injectable } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { Store } from '@ngrx/store';
import { SecurityState } from './security.state';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { loginAction } from '../components/login/login-component.actions';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';
import { AuthenticationRequest } from '../interfaces/request/authentication.request';
import {
  initUserFromCookieFailure,
  initUserFromCookieSuccess,
  loginFailure,
  loginSuccess,
  logoutFailure,
  logoutSuccess,
  navigateToLoginFailure,
  navigateToLoginSuccess,
  registerUserFailure,
  registerUserSuccess
} from '../services/authentication-service.actions';
import { Router } from '@angular/router';
import { from, of } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { initUserFromCookie, logoutUser } from '../../navigation/components/nav-bar/nav-bar-component.actions';
import { navigateToLogin } from '../guard/guard.actions';
import { registerUser } from '../components/register/register-component.actions';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class SecurityEffects {

  constructor(
    private actions$: Actions,
    private _authService: AuthenticationService,
    private _store: Store<SecurityState>,
    private _router: Router,
    private _cookie: CookieService,
    private _toast: ToastrService
  ) {}

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loginAction),
      mergeMap(action => this._authService.login({ password: action.password, username: action.username } as AuthenticationRequest).pipe(
        map(response => {
          this._toast.success('User logged in successfully!');
          return loginSuccess({ username: response.username, token: response.token });
        }),
        tap(() => this._router.navigate(['/'])),
        catchError(error => {
          this._toast.error(error.message, 'Log in failed!');
          return of(loginFailure({ error }));
        })
      ))
    )
  );

  logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(logoutUser),
      mergeMap(() => this._authService.logout().pipe(
        map(() => {
          this._toast.success('User logged out successfully!');
          return logoutSuccess();
        }),
        tap(() => this._router.navigate(['authenticate/login'])),
        catchError(error => {
          this._toast.error(error.message, 'Logout failed!');
          return of(logoutFailure({ error }));
        })
      ))
    )
  );

  navigateToLogin$ = createEffect(() =>
    this.actions$.pipe(
      ofType(navigateToLogin),
      mergeMap(() => from(this._router.navigate(['authenticate/login'])).pipe(
        map(() => {
          this._toast.warning('You have to login to access this page!');
          return navigateToLoginSuccess();
        }),
        catchError(error => of(navigateToLoginFailure({ error })))
      ))
    )
  );

  register$ = createEffect(() =>
    this.actions$.pipe(
      ofType(registerUser),
      mergeMap(action => this._authService.register(action.request).pipe(
        map(() => {
          this._toast.success('User registered successfully!');
          return registerUserSuccess();
        }),
        tap(() => this._router.navigate(['authenticate/login'])),
        catchError(error => {
          this._toast.success(error.message, 'User registered failed!');
          return of(registerUserFailure({ error }));
        })
      ))
    ));

  initUserFromCookie$ = createEffect(() =>
    this.actions$.pipe(
      ofType(initUserFromCookie),
      mergeMap(() => this._authService.getUserFromCookies().pipe(
        map(userInfo => initUserFromCookieSuccess({ username: userInfo.username, token: userInfo.token })),
        catchError(error => of(initUserFromCookieFailure({ error })))
      ))
    )
  );

}
