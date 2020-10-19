import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { SurveyService } from '../services/survey.service';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';
import * as fromHomeActions from '../pages/home/home.actions';
import * as fromSurveyQuestionActions from '../components/survey-question-create/survey-question-create.actions';
import * as fromSurveyEditPageActions from '../pages/survey-edit/survey-edit-page.actions';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { SurveyQuestionService } from '../services/survey-question.service';

@Injectable()
export class SurveyEffects {

  constructor(
    private actions$: Actions,
    private _surveyService: SurveyService,
    private _surveyQuestionService: SurveyQuestionService,
    private _router: Router
  ) {}

  createSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromHomeActions.createSurvey),
      mergeMap(_ => this._surveyService.createSurvey().pipe(
        map(survey => fromHomeActions.createSurveySuccess({ survey })),
        tap(it => this._router.navigate([`survey/${it.survey.naturalKey}`])),
        catchError(error => of(fromHomeActions.createSurveyFailure({ error })))
        )
      )
    )
  );

  createSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromSurveyQuestionActions.addSurveyQuestion),
      mergeMap(_ =>
        this._surveyQuestionService.createSurveyQuestion().pipe(
          map(surveyQuestion => fromSurveyQuestionActions.addSurveyQuestionSuccess({ surveyQuestion })),
          catchError(error => of(fromSurveyQuestionActions.addSurveyQuestionFailure({ error })))
        )
      )
    )
  );

  findSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromSurveyEditPageActions.findSurvey),
      mergeMap(action =>
        this._surveyService.findSurveyByNaturalKey(action.naturalKey).pipe(
          map(survey => fromSurveyEditPageActions.findSurveySuccess({ survey })),
          catchError(error => of(fromSurveyEditPageActions.findSurveyFailure({ error })))
        )
      )
    )
  );

  // editSurvey$ = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType(fromSurveyCreateActions.editSurvey),
  //     mergeMap((state) => {
  //         return this.surveyService.createSurvey().pipe(
  //           map(survey => fromSurveyCreateActions.editSurveySuccess({ survey })),
  //           catchError(error => of(fromSurveyCreateActions.editSurveyCreateFailure({ error })))
  //         );
  //       }
  //     )
  //   )
  // );
}
