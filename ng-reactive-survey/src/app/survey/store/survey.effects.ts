import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { SurveyService } from '../services/survey/survey.service';
import { catchError, map, mergeMap, tap, withLatestFrom } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { SurveyQuestionService } from '../services/survey-question/survey-question.service';
import {
  createSurveyFailure,
  createSurveySuccess,
  editSurveyCreateFailure,
  editSurveySuccess,
  findSurveyFailure,
  findSurveySuccess
} from '../services/survey/survey.actions';
import { findSurvey } from '../pages/survey-edit/survey-edit-page.actions';
import {
  addSurveyQuestion,
  deleteSurveyQuestion,
  duplicateSurveyQuestion,
  editSurveyQuestion,
  updateSurveyQuestionPosition
} from '../components/survey-question-create/survey-question-create.actions';
import { createSurvey } from '../pages/home/home.actions';
import {
  addSurveyQuestionFailure,
  addSurveyQuestionSuccess,
  deleteSurveyQuestionFailure,
  deleteSurveyQuestionSuccess,
  duplicateSurveyQuestionFailure,
  duplicateSurveyQuestionSuccess,
  editSurveyQuestionFailure,
  editSurveyQuestionSuccess,
  updateSurveyQuestionPositionFailure,
  updateSurveyQuestionPositionSuccess
} from '../services/survey-question/survey-question.actions';
import { editSurvey } from '../components/survey-create/survey-create.actions';
import { SurveyState } from './survey.state';
import { Store } from '@ngrx/store';

@Injectable()
export class SurveyEffects {

  constructor(
    private actions$: Actions,
    private _surveyService: SurveyService,
    private _surveyQuestionService: SurveyQuestionService,
    private _router: Router,
    private _state: Store<SurveyState>
  ) {}

  createSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(createSurvey),
      mergeMap(_ => this._surveyService.createSurvey().pipe(
        map(survey => createSurveySuccess({ survey })),
        tap(it => this._router.navigate([`survey/${it.survey.naturalKey}`])),
        catchError(error => of(createSurveyFailure({ error })))
        )
      )
    )
  );

  findSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findSurvey),
      mergeMap(action =>
        this._surveyService.findSurveyByNaturalKey(action.naturalKey).pipe(
          map(survey => findSurveySuccess({ survey })),
          catchError(error => of(findSurveyFailure({ error })))
        )
      )
    )
  );

  editSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(editSurvey),
      withLatestFrom(this._state),
      mergeMap(([action, state]) =>
        this._surveyService.editSurveyInfo(state.survey.id, action.surveyEditInfo).pipe(
          map(survey => editSurveySuccess({ survey })),
          catchError(error => of(editSurveyCreateFailure({ error })))
        )
      )
    )
  );

  createSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(addSurveyQuestion),
      mergeMap(_ =>
        this._surveyQuestionService.createSurveyQuestion().pipe(
          map(surveyQuestion => addSurveyQuestionSuccess({ surveyQuestion })),
          catchError(error => of(addSurveyQuestionFailure({ error })))
        )
      )
    )
  );

  editSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(editSurveyQuestion),
      mergeMap(action =>
        this._surveyQuestionService.editSurveyQuestion(action.id, action.changes).pipe(
          map(surveyQuestion => editSurveyQuestionSuccess({ surveyQuestion })),
          catchError(error => of(editSurveyQuestionFailure({ error })))
        )
      )
    )
  );

  updateSurveyQuestionPosition$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateSurveyQuestionPosition),
      //todo delete this
      withLatestFrom(this._state),
      mergeMap(([action, state]) =>
        this._surveyQuestionService.updateSurveyQuestionPosition(action.id, action.previousIndex, action.currentIndex,
          //todo delete this
          state.survey.questions).pipe(
          map(surveyQuestions => updateSurveyQuestionPositionSuccess({ surveyQuestions })),
          catchError(error => of(updateSurveyQuestionPositionFailure({ error })))
        )
      )
    )
  );

  duplicateSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(duplicateSurveyQuestion),
      withLatestFrom(this._state),
      mergeMap(([action, state]) =>
        this._surveyQuestionService.duplicateQuestion(action.question,
          //todo delete this
          state.survey.questions).pipe(
          map(surveyQuestions => duplicateSurveyQuestionSuccess({ surveyQuestions })),
          catchError(error => of(duplicateSurveyQuestionFailure({ error })))
        ))
    ));

  deleteSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(deleteSurveyQuestion),
      mergeMap(action =>
        this._surveyQuestionService.deleteSurveyQuestion(action.id).pipe(
          map(surveyQuestionId => deleteSurveyQuestionSuccess({ surveyQuestionId })),
          catchError(error => of(deleteSurveyQuestionFailure({ error })))
        )
      )
    )
  );

}
