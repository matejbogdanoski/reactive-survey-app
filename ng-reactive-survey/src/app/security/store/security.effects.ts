import { Injectable } from '@angular/core';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { Store } from '@ngrx/store';
import { SecurityState } from './security.state';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { loginAction } from '../components/login/login-component.actions';
import { catchError, map, mergeMap, tap, withLatestFrom } from 'rxjs/operators';
import { AuthenticationRequest } from '../interfaces/request/authentication.request';
import {
  initUserFromCookieFailure,
  initUserFromCookieSuccess,
  loginFailure,
  loginSuccess,
  logoutFailure,
  logoutSuccess,
  navigateToLoginFailure,
  navigateToLoginSuccess
} from '../services/authentication/authentication-service.actions';
import { Router } from '@angular/router';
import { from, of } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { initUserFromCookie, logoutUser } from '../../navigation/components/nav-bar/nav-bar-component.actions';
import { navigateToLogin } from '../guard/guard.actions';
import { registerUser } from '../components/register/register-component.actions';
import { ToastrService } from 'ngx-toastr';
import {
  findUserInfoFailure,
  findUserInfoSuccess,
  registerUserFailure,
  registerUserSuccess,
  updatePasswordFailure,
  updatePasswordSuccess,
  updateUserInfoFailure,
  updateUserInfoSuccess
} from '../services/user/user-service.actions';
import { UserService } from '../services/user/user.service';
import { findUserInfo, updateUserInfo } from '../components/edit-profile/edit-profile-component.actions';
import { updatePassword } from '../components/reset-password/reset-password-component.actions';
import { selectSecurityState } from './security.selectors';

@Injectable()
export class SecurityEffects {

  constructor(
    private actions$: Actions,
    private _authService: AuthenticationService,
    private _userService: UserService,
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
          this._toast.error(error.error, 'Log in failed!');
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
        tap(() => this._router.navigate(['user/login'])),
        catchError(error => {
          this._toast.error(error.error, 'Logout failed!');
          return of(logoutFailure({ error }));
        })
      ))
    )
  );

  navigateToLogin$ = createEffect(() =>
    this.actions$.pipe(
      ofType(navigateToLogin),
      mergeMap(() => from(this._router.navigate(['user/login'])).pipe(
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
      mergeMap(action => this._userService.register(action.request).pipe(
        map(() => {
          this._toast.success('User registered successfully!');
          return registerUserSuccess();
        }),
        tap(() => this._router.navigate(['user/login'])),
        catchError(error => {
          this._toast.success(error.error, 'User registered failed!');
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

  findUserInfo$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findUserInfo),
      mergeMap(() => this._userService.findUserInfo().pipe(
        map(userInfo => findUserInfoSuccess({ userInfo })),
        catchError(error => of(findUserInfoFailure({ error })))
      ))
    )
  );

  updateUserInfo$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateUserInfo),
      mergeMap(action => this._userService.updateUserInfo(action.userId, action.updateUserInfoRequest).pipe(
        map(userInfo => {
          this._toast.success('Successfully updated user info!');
          return updateUserInfoSuccess({ userInfo });
        }),
        catchError(error => {
          this._toast.error(error.error);
          return of(updateUserInfoFailure({ error }));
        })
      ))
    )
  );

  updatePassword$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updatePassword),
      withLatestFrom(this._store.select(selectSecurityState)),
      mergeMap(([action, state]) => this._userService.updatePassword(action.updatePasswordRequest, state).pipe(
        map(authResponse => {
          this._toast.success('Successfully updated password!');
          this._authService.updateCookies(authResponse.username, authResponse.token);
          return updatePasswordSuccess({ username: authResponse.username, token: authResponse.token });
        }),
        tap(() => this._router.navigate(['/'])),
        catchError(error => {
          this._toast.error(error.error);
          return of(updatePasswordFailure({ error }));
        })
      ))
    )
  );

}
