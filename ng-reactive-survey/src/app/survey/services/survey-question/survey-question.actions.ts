import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

const source = '[Survey Question API]';

export const addSurveyQuestionSuccess = createAction(`${source} Add Survey Question Success`, props<{ surveyQuestion: SurveyQuestion }>());
export const addSurveyQuestionFailure = createAction(`${source} Add Survey Question Failure`, props<{ error: any }>());
