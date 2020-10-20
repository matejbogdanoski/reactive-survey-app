import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SurveyService } from '../../services/survey/survey.service';
import { SurveyState } from '../../store/survey.state';
import { Store } from '@ngrx/store';
import { selectSurvey } from '../../store/survey.selectors';
import { findSurvey } from './survey-edit-page.actions';
import { takeUntil, tap, withLatestFrom } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  templateUrl: './survey-edit.page.html',
  styleUrls: ['./survey-edit.page.scss']
})
export class SurveyEditPage implements OnInit, OnDestroy {

  private _destroySubject = new Subject<void>();

  constructor(
    private _route: ActivatedRoute,
    private _surveyService: SurveyService,
    private _state: Store<SurveyState>
  ) { }

  ngOnInit(): void {
    this._state.select(selectSurvey).pipe(
      takeUntil(this._destroySubject),
      withLatestFrom(this._route.paramMap),
      tap(([survey, params]) => {
        const naturalKey = params.get('naturalKey');
        if (survey.naturalKey !== naturalKey) {
          //dispatch action to find the survey from backend
          this._state.dispatch(findSurvey({ naturalKey }));
        }
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    this._destroySubject.next();
  }

}
