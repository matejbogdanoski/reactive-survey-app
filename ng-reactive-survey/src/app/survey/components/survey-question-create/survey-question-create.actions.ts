import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

//Add survey question
export const addSurveyQuestion = createAction(
  '[Survey Question Create Component] Add Survey Question'
);

export const addSurveyQuestionSuccess = createAction(
  '[Survey Question Create Effect] Add Survey Question Success', props<{ surveyQuestion: SurveyQuestion }>()
);

export const addSurveyQuestionFailure = createAction(
  '[Survey Question Create Effect] Add Survey Question Failure', props<{ error: any }>()
);

export const updateSurveyQuestion = createAction(
  '[Survey Question] Update', props<{
    surveyQuestion: SurveyQuestion,
    changes: Partial<SurveyQuestion>
  }>()
);

export const deleteSurveyQuestion = createAction(
  '[Survey Question] Delete', props<{ surveyQuestion: SurveyQuestion }>()
);
