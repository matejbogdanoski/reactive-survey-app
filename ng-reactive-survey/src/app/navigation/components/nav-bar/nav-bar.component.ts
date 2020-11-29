import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { SecurityState } from '../../../security/store/security.state';
import { Observable } from 'rxjs';
import { selectUsername } from '../../../security/store/security.selectors';
import { CookieService } from 'ngx-cookie-service';
import { initUserFromCookie, logoutUser } from './nav-bar-component.actions';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  user$: Observable<string>;

  constructor(
    private _store: Store<SecurityState>,
    private _cookie: CookieService
  ) { }

  ngOnInit(): void {
    this._store.dispatch(initUserFromCookie());
    this.user$ = this._store.select(selectUsername);
  }

  logout() {
    this._store.dispatch(logoutUser());
  }

}
