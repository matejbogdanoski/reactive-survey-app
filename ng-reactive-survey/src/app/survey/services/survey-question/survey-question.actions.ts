import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

const source = '[Survey Question API]';

export const addSurveyQuestionSuccess = createAction(`${source} Add Survey Question Success`, props<{ surveyQuestion: SurveyQuestion }>());
export const addSurveyQuestionFailure = createAction(`${source} Add Survey Question Failure`, props<{ error: any }>());

export const editSurveyQuestionSuccess = createAction(`${source} Edit Survey Question Success`,
  props<{ surveyQuestion: SurveyQuestion }>());
export const editSurveyQuestionFailure = createAction(`${source} Edit Survey Question Failure`, props<{ error: any }>());

export const deleteSurveyQuestionSuccess = createAction(`${source} Delete Survey Question Success`, props<{ surveyQuestionId: number }>());
export const deleteSurveyQuestionFailure = createAction(`${source} Delete Survey Question Failure`, props<{ error: any }>());
