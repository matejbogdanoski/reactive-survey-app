import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

const source = '[Survey Question Create Component]';

export const addSurveyQuestion = createAction(`${source} Add Survey Question`);

export const editSurveyQuestion = createAction(`${source} Update`, props<{ id: number, changes: Partial<SurveyQuestion> }>());

export const deleteSurveyQuestion = createAction(`${source} Delete`, props<{ id: number }>());
