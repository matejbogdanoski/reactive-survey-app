import { createAction, props } from '@ngrx/store';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';

//Add survey question
export const addSurveyQuestion = createAction(
  '[Survey Question Create Component] Add Survey Question', props<{ surveyQuestion: SurveyQuestionCreate }>()
);

export const addSurveyQuestionSuccess = createAction(
  '[Survey Question Create Effect] Add Survey Question Success', props<{ surveyQuestion: SurveyQuestionCreate }>()
);

export const addSurveyQuestionFailure = createAction(
  '[Survey Question Create Effect] Add Survey Question Failure', props<{ error: any}>()
);

export const updateSurveyQuestion = createAction(
  '[Survey Question] Update', props<{
    surveyQuestion: SurveyQuestionCreate,
    changes: Partial<SurveyQuestionCreate>
  }>()
);

export const deleteSurveyQuestion = createAction(
  '[Survey Question] Delete', props<{ surveyQuestion: SurveyQuestionCreate }>()
);
