import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

const source = '[Survey Question Create Component]';

export const addSurveyQuestion = createAction(`${source} Add Survey Question`);

export const updateSurveyQuestion = createAction(`${source} Update`,
  props<{ surveyQuestion: SurveyQuestion, changes: Partial<SurveyQuestion> }>());

export const deleteSurveyQuestion = createAction(`${source} Delete`, props<{ surveyQuestion: SurveyQuestion }>());
