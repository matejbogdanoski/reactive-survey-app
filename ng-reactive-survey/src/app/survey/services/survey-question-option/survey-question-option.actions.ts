import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../../interfaces/survey-question.interface';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';

const source = '[Survey Question Options API]';

export const addSurveyQuestionOptionSuccess = createAction(`${source} Add Survey Question Option Success`,
  props<{ surveyQuestion: SurveyQuestion, questionOption: SurveyQuestionOption }>());
export const addSurveyQuestionOptionFailure = createAction(`${source} Add Survey Question Option Failure`, props<{ error: any }>());

export const findSurveyQuestionOptionsSuccess = createAction(`${source} Find Survey Question Options Success`,
  props<{ surveyQuestionId: number, questionOptions: SurveyQuestionOption[] }>());
export const findSurveyQuestionOptionsFailure = createAction(`${source} Find Survey Question Options Failure`, props<{ error: any }>());

export const deleteSurveyQuestionOptionSuccess = createAction(`${source} Delete Survey Question Option Success`,
  props<{ surveyQuestion: SurveyQuestion, questionOptionId: number }>());
export const deleteSurveyQuestionOptionFailure = createAction(`${source} Delete Survey Question Option Failure`, props<{ error: any }>());

export const updateQuestionOptionPositionSuccess = createAction(`${source} Update Survey Question Option Position Success`,
  props<{ surveyQuestion: SurveyQuestion, questionOptions: SurveyQuestionOption[] }>());
export const updateQuestionOptionPositionFailure = createAction(`${source} Update Survey Question Option Position Failure`,
  props<{ error: any }>());

export const updateQuestionOptionLabelSuccess = createAction(`${source} Update Survey Question Option Label Success`,
  props<{ surveyQuestion: SurveyQuestion, questionOption: SurveyQuestionOption }>());
export const updateQuestionOptionLabelFailure = createAction(`${source} Update Survey Question Option Label Failure`,
  props<{ error: any }>());
