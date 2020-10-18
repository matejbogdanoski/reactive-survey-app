import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, withLatestFrom } from 'rxjs/operators';
import { of } from 'rxjs';
import * as fromSurveyCreateActions from './survey-create.actions';
import { SurveyService } from '../services/survey.service';
import { SurveyState } from '../survey-state';
import { Store } from '@ngrx/store';

@Injectable()
export class SurveyCreateEffects {

  createSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromSurveyCreateActions.createSurvey),
      withLatestFrom(this.store$),
      mergeMap(([action, state]) => {
          return this.surveyService.createSurvey(state).pipe(
            map(survey => fromSurveyCreateActions.addSurveyCreateSuccess({ survey })),
            catchError(error => of(fromSurveyCreateActions.createSurveyCreateFailure({ error })))
          );
        }
      )
    )
  );

  constructor(
    private actions$: Actions,
    private surveyService: SurveyService,
    private store$: Store<SurveyState>
  ) {}

}
