import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { confirmPasswordValidator } from '../../validators/confirm-password.validator';
import { UpdatePasswordRequest } from '../../interfaces/request/update-password.request';
import { Store } from '@ngrx/store';
import { SecurityState } from '../../store/security.state';
import { updatePassword } from './reset-password-component.actions';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ResetPasswordComponent implements OnInit {

  form: FormGroup = this.initForm;

  constructor(
    private _fb: FormBuilder,
    private _store: Store<SecurityState>
  ) { }

  ngOnInit(): void {
  }

  private get initForm() {
    return this._fb.group({
      oldPassword: [''],
      newPassword: [''],
      confirmNewPassword: ['']
    }, {
      validators: [confirmPasswordValidator('newPassword', 'confirmNewPassword')]
    });
  }

  public submit() {
    const updatePasswordRequest = this.form.getRawValue() as UpdatePasswordRequest;
    this._store.dispatch(updatePassword({ updatePasswordRequest }));
    this.form.reset();
  }

  public get nonMatchingPasswords(): boolean {
    return !!this.form.get('confirmNewPassword').errors?.confirmPasswordValidator;
  }

}
