import { createAction, props } from '@ngrx/store';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';

export const addSurveyQuestion = createAction(
  '[Survey Question] Add Survey Question', props<{ surveyQuestion: SurveyQuestionCreate }>()
);

export const updateSurveyQuestion = createAction(
  '[Survey Question] Update', props<{
    id: number,
    changes: Partial<SurveyQuestionCreate>
  }>()
);

export const deleteSurveyQuestion = createAction(
  '[Survey Question] Delete', props<{ id: number }>()
);
