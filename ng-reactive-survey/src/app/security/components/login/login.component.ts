import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Store } from '@ngrx/store';
import { SecurityState } from '../../store/security.state';
import { loginAction } from './login-component.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form = this.initialForm;

  constructor(
    private _builder: FormBuilder,
    private _store: Store<SecurityState>
  ) { }

  ngOnInit(): void {
  }

  login() {
    const value = this.form.getRawValue();
    const [username, password] = [value.username, value.password];
    this._store.dispatch(loginAction({ username, password }));
  }

  private get initialForm() {
    return this._builder.group({
      username: [''],
      password: ['']
    });
  }

}
