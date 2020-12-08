import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SurveyService } from '../../services/survey/survey.service';
import { SurveyState } from '../../store/survey.state';
import { Store } from '@ngrx/store';
import { selectSurvey } from '../../store/survey.selectors';
import { findSurvey, previewSurvey } from './survey-edit-page.actions';
import { takeUntil, tap, withLatestFrom } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { InvitationsDialog } from '../../dialogs/invitations/invitations.dialog';

@Component({
  templateUrl: './survey-edit.page.html',
  styleUrls: ['./survey-edit.page.scss']
})
export class SurveyEditPage implements OnInit, OnDestroy {

  private _destroySubject = new Subject<void>();

  surveyId: number;
  surveyNaturalKey: string;

  constructor(
    private _route: ActivatedRoute,
    private _surveyService: SurveyService,
    private _store: Store<SurveyState>,
    private _dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this._store.select(selectSurvey).pipe(
      takeUntil(this._destroySubject),
      withLatestFrom(this._route.paramMap),
      tap(([survey, params]) => {
        const id = +params.get('id');
        this.surveyId = id;
        if (survey.id !== id) {
          //dispatch action to find the survey from backend
          this._store.dispatch(findSurvey({ id }));
        }
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    this._destroySubject.next();
  }

  openInvitationsDialog() {
    this._dialog.open(InvitationsDialog, { data: { surveyId: this.surveyId } });
  }

  routeToPreview() {
    this._store.dispatch(previewSurvey());
  }

}
