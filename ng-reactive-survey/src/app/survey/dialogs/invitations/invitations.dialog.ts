import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Store } from '@ngrx/store';
import { SurveyState } from '../../store/survey.state';
import { selectSurveyInvitations } from '../../store/survey.selectors';
import { Observable } from 'rxjs';
import { SurveyInvitation } from '../../interfaces/survey-invitation.interface';
import { createInvitation, findInvitations } from './invitations-dialog.actions';
import { filter } from 'rxjs/operators';
import * as _ from 'lodash';
import { FormBuilder } from '@angular/forms';

interface InvitationsDialogData {
  surveyId: number;
}

@Component({
  templateUrl: './invitations.dialog.html',
  styleUrls: ['./invitations.dialog.scss']
})
export class InvitationsDialog implements OnInit {

  surveyId: number;
  surveyInvitations$: Observable<SurveyInvitation[]>;
  form = this.initForm;

  constructor(
    private _dialogRef: MatDialogRef<InvitationsDialog>,
    private _store: Store<SurveyState>,
    private _fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: InvitationsDialogData
  ) { }

  ngOnInit(): void {
    this.surveyId = this.data.surveyId;
    this._store.dispatch(findInvitations({ surveyId: this.surveyId }));
    this.surveyInvitations$ = this._store.select(selectSurveyInvitations).pipe(
      filter(it => !_.isEmpty(it))
    );
  }

  submit() {
    const username = this.form.get('username').value;
    this._store.dispatch(createInvitation({ surveyId: this.surveyId, username }));
  }

  close() {
    this._dialogRef.close();
  }

  private get initForm() {
    return this._fb.group({
      username: ['']
    });
  }

}
