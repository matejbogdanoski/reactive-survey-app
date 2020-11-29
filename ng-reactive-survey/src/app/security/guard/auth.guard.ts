import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Store } from '@ngrx/store';
import { SecurityState } from '../store/security.state';
import { Observable } from 'rxjs';
import { selectToken } from '../store/security.selectors';
import { first, map } from 'rxjs/operators';
import { navigateToLogin } from './guard.actions';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private _store: Store<SecurityState>
  ) {}

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.checkStoreAuthentication().pipe(
      map(authed => {
        if (!authed) {
          this._store.dispatch(navigateToLogin());
          return false;
        }

        return true;
      }),
      first()
    );
  }

  private checkStoreAuthentication(): Observable<Boolean> {
    return this._store.select(selectToken).pipe(
      map(token => !!token),
      first()
    );
  }

}
