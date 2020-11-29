import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserCreateRequest } from '../../interfaces/request/user-create.request';
import { Store } from '@ngrx/store';
import { SecurityState } from '../../store/security.state';
import { registerUser } from './register-component.actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form = this.initForm;

  constructor(
    private _formBuilder: FormBuilder,
    private _store: Store<SecurityState>
  ) { }

  ngOnInit(): void {
  }

  register() {
    const request = this.form.getRawValue() as UserCreateRequest;
    this._store.dispatch(registerUser({ request }));
  }

  private get initForm() {
    return this._formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.email],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    });
  }

}
