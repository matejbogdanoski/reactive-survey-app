import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Router } from '@angular/router';
import { SurveyRendererService } from '../services/survey-renderer/survey-renderer.service';
import { findFullSurvey } from '../pages/take-survey/take-survey-page.actions';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { findFullSurveyFailure, findFullSurveySuccess } from '../services/survey-renderer/survey-renderer-service.actions';
import { of } from 'rxjs';

@Injectable()
export class TakeSurveyEffects {
  constructor(
    private actions$: Actions,
    private _router: Router,
    private _surveyRendererService: SurveyRendererService
  ) {}

  findFullSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findFullSurvey),
      mergeMap(action =>
        this._surveyRendererService.findFullSurveyByNaturalKey(action.naturalKey).pipe(
          map(survey => findFullSurveySuccess({ survey })),
          catchError(error => of(findFullSurveyFailure({ error })))
        )
      )
    )
  );

}
