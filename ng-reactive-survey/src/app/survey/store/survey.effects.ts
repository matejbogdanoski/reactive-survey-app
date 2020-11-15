import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { SurveyService } from '../services/survey/survey.service';
import { catchError, map, mergeMap, switchMap, tap, withLatestFrom } from 'rxjs/operators';
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
  addQuestionOption,
  addSurveyQuestion,
  deleteQuestionOption,
  deleteSurveyQuestion,
  duplicateSurveyQuestion,
  editSurveyQuestion,
  findSurveyQuestionOptions,
  updateQuestionOptionLabel,
  updateQuestionOptionPosition,
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
  findSurveyQuestionsFailure,
  findSurveyQuestionsSuccess,
  updateSurveyQuestionPositionFailure,
  updateSurveyQuestionPositionSuccess
} from '../services/survey-question/survey-question.actions';
import { editSurvey, findSurveyQuestions } from '../components/survey-create/survey-create.actions';
import { SurveyState } from './survey.state';
import { Store } from '@ngrx/store';
import { SurveyQuestionOptionService } from '../services/survey-question-option/survey-question-option.service';
import {
  addSurveyQuestionOptionFailure,
  addSurveyQuestionOptionSuccess,
  deleteSurveyQuestionOptionFailure,
  deleteSurveyQuestionOptionSuccess,
  findSurveyQuestionOptionsFailure,
  findSurveyQuestionOptionsSuccess,
  updateQuestionOptionLabelFailure,
  updateQuestionOptionLabelSuccess,
  updateQuestionOptionPositionFailure,
  updateQuestionOptionPositionSuccess
} from '../services/survey-question-option/survey-question-option.actions';
import { hasOptionsStatic } from '../enum/question-type.enum';
import { findSurveyInstances } from '../components/survey-responses/survey-responses-component.actions';
import { SurveyInstanceService } from '../../shared/services/survey-instance/survey-instance.service';
import {
  findSurveyInstancesFailure,
  findSurveyInstancesSuccess
} from '../../shared/services/survey-instance/survey-instance-service.actions';

@Injectable()
export class SurveyEffects {

  constructor(
    private actions$: Actions,
    private _surveyService: SurveyService,
    private _surveyQuestionService: SurveyQuestionService,
    private _surveyQuestionOptionService: SurveyQuestionOptionService,
    private _surveyInstanceService: SurveyInstanceService,
    private _router: Router,
    private _state: Store<SurveyState>
  ) {}

  //Survey Effects
  createSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(createSurvey),
      mergeMap(_ => this._surveyService.createSurvey().pipe(
        map(survey => createSurveySuccess({ survey })),
        tap(it => this._router.navigate([`survey/${it.survey.id}`])),
        catchError(error => of(createSurveyFailure({ error })))
        )
      )
    )
  );

  findSurvey$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findSurvey),
      mergeMap(action =>
        this._surveyService.findSurveyById(action.id).pipe(
          switchMap(survey => [findSurveyQuestions({ surveyId: survey.id }), findSurveySuccess({ survey })]),
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
        this._surveyService.editSurveyInfo(state.survey, action.surveyEditInfo).pipe(
          map(survey => editSurveySuccess({ survey })),
          catchError(error => of(editSurveyCreateFailure({ error })))
        )
      )
    )
  );

  //Survey Question Effects
  createSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(addSurveyQuestion),
      withLatestFrom(this._state),
      mergeMap(([_, state]) =>
        this._surveyQuestionService.createSurveyQuestion(state.survey).pipe(
          map(surveyQuestion => addSurveyQuestionSuccess({ surveyQuestion })),
          catchError(error => of(addSurveyQuestionFailure({ error })))
        )
      )
    )
  );

  findSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findSurveyQuestions),
      mergeMap(action =>
        this._surveyQuestionService.findAllBySurvey(action.surveyId).pipe(
          switchMap(surveyQuestions =>
            [...surveyQuestions.filter(q => hasOptionsStatic(q.questionType)).map(
              q => findSurveyQuestionOptions({ surveyQuestionId: q.id })),
             findSurveyQuestionsSuccess({ surveyQuestions })]
          ),
          catchError(error => of(findSurveyQuestionsFailure({ error })))
        )
      )
    )
  );

  editSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(editSurveyQuestion),
      withLatestFrom(this._state),
      mergeMap(([action, state]) =>
        this._surveyQuestionService.editSurveyQuestion(action.id, action.changes, state.survey).pipe(
          map(surveyQuestion => editSurveyQuestionSuccess({ surveyQuestion })),
          catchError(error => of(editSurveyQuestionFailure({ error })))
        )
      )
    )
  );

  updateSurveyQuestionPosition$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateSurveyQuestionPosition),
      withLatestFrom(this._state),
      mergeMap(([action, state]) =>
        this._surveyQuestionService.updateSurveyQuestionPosition(action.id, action.previousIndex,
          action.currentIndex, state.survey).pipe(
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
        this._surveyQuestionService.duplicateQuestion(action.question, state.survey).pipe(
          map(surveyQuestions => duplicateSurveyQuestionSuccess({ surveyQuestions })),
          catchError(error => of(duplicateSurveyQuestionFailure({ error })))
        ))
    ));

  deleteSurveyQuestion$ = createEffect(() =>
    this.actions$.pipe(
      ofType(deleteSurveyQuestion),
      withLatestFrom(this._state),
      mergeMap(([action, state]) =>
        this._surveyQuestionService.deleteSurveyQuestion(action.id, state.survey).pipe(
          map(surveyQuestionId => deleteSurveyQuestionSuccess({ surveyQuestionId })),
          catchError(error => of(deleteSurveyQuestionFailure({ error })))
        )
      )
    )
  );

  //Survey Question Option Effects
  findSurveyQuestionOptions$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findSurveyQuestionOptions),
      mergeMap(action =>
        this._surveyQuestionOptionService.findAllByQuestion(action.surveyQuestionId).pipe(
          map(questionOptions => findSurveyQuestionOptionsSuccess({ surveyQuestionId: action.surveyQuestionId, questionOptions })),
          catchError(error => of(findSurveyQuestionOptionsFailure({ error })))
        )
      )
    )
  );

  addQuestionOption$ = createEffect(() =>
    this.actions$.pipe(
      ofType(addQuestionOption),
      mergeMap(action =>
        this._surveyQuestionOptionService.addNewQuestionOption(action.surveyQuestion).pipe(
          map(option => addSurveyQuestionOptionSuccess({
            surveyQuestion: action.surveyQuestion,
            questionOption: option
          })),
          catchError(error => of(addSurveyQuestionOptionFailure({ error })))
        )
      )
    )
  );

  deleteQuestionOption$ = createEffect(() =>
    this.actions$.pipe(
      ofType(deleteQuestionOption),
      mergeMap(action =>
        this._surveyQuestionOptionService.deleteQuestionOption(action.surveyQuestion, action.surveyQuestionOption).pipe(
          map(questionOptionId => deleteSurveyQuestionOptionSuccess({ surveyQuestion: action.surveyQuestion, questionOptionId })),
          catchError(error => of(deleteSurveyQuestionOptionFailure({ error })))
        )
      )
    )
  );

  updateQuestionOptionPosition$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateQuestionOptionPosition),
      mergeMap(action =>
        this._surveyQuestionOptionService.updateQuestionOptionPosition(action.surveyQuestion, action.optionId,
          action.previousIndex, action.currentIndex).pipe(
          map(questionOptions => updateQuestionOptionPositionSuccess({ surveyQuestion: action.surveyQuestion, questionOptions })),
          catchError(error => of(updateQuestionOptionPositionFailure({ error })))
        )
      )
    )
  );

  updateQuestionOptionLabel$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateQuestionOptionLabel),
      mergeMap(action =>
        this._surveyQuestionOptionService.updateQuestionOptionLabel(action.surveyQuestion, action.optionId, action.changedLabel).pipe(
          map(questionOption => updateQuestionOptionLabelSuccess({ questionOption, surveyQuestion: action.surveyQuestion })),
          catchError(error => of(updateQuestionOptionLabelFailure({ error })))
        )
      )
    )
  );

  //Survey Instance Effects
  findSurveyInstances$ = createEffect(() =>
    this.actions$.pipe(
      ofType(findSurveyInstances),
      mergeMap(action => this._surveyInstanceService.findAllInstancesBySurveyId(action.surveyId).pipe(
        map(surveyInstances => findSurveyInstancesSuccess({ surveyInstances })),
        catchError(error => of(findSurveyInstancesFailure({ error })))
        )
      )
    )
  );

}
