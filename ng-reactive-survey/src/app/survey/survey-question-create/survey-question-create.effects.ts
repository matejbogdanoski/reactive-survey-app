import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as fromSurveyQuestionActions from './survey-question-create.actions';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { SurveyQuestionService } from '../services/survey-question.service';
import { of } from 'rxjs';

@Injectable()
export class SurveyQuestionCreateEffects {

  // createQuestionSurvey$ = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType(fromSurveyQuestionActions.addSurveyQuestion),
  //     mergeMap(action =>
  //       this.surveyQuestionService.createSurveyQuestion(action.surveyQuestion).pipe(
  //         map(surveyQuestion => fromSurveyQuestionActions.addSurveyQuestionSuccess({ surveyQuestion })),
  //         catchError(error => of(fromSurveyQuestionActions.addSurveyQuestionFailure({ error })))
  //       )
  //     )
  //   )
  // );

  constructor(
    private actions$: Actions,
    private surveyQuestionService: SurveyQuestionService
  ) {}

}
