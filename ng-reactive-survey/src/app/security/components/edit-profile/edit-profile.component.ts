import { Component, OnDestroy, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { SecurityState } from '../../store/security.state';
import { findUserInfo, updateUserInfo } from './edit-profile-component.actions';
import { Subject } from 'rxjs';
import { selectUserInfo } from '../../store/security.selectors';
import { FormBuilder } from '@angular/forms';
import { patchFormValues } from '../../../operators/patch-form-values.rx-operator';
import { filter, tap } from 'rxjs/operators';
import * as _ from 'lodash';
import { UpdateUserInfoRequest } from '../../interfaces/request/update-user-info.request';

@Component({
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit, OnDestroy {

  form = this.initForm;
  userId: number;

  private _destroySubject = new Subject<void>();

  constructor(
    private _store: Store<SecurityState>,
    private _fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this._store.dispatch(findUserInfo());
    this._store.select(selectUserInfo).pipe(
      filter(it => !_.isEmpty(it)),
      tap(userInfo => this.userId = userInfo.id),
      patchFormValues(this.form)
    ).subscribe(() =>
      this.form.get('username').disable()
    );
  }

  submit() {
    const updateUserInfoRequest = this.form.getRawValue() as UpdateUserInfoRequest;
    this._store.dispatch(updateUserInfo({ updateUserInfoRequest, userId: this.userId }));
  }

  private get initForm() {
    return this._fb.group({
      username: [''],
      email: [''],
      firstName: [''],
      lastName: ['']
    });
  }

  ngOnDestroy(): void {
    this._destroySubject.next();
  }

}


