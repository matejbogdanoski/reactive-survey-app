import { createAction, props } from '@ngrx/store';
import { SurveyCreate } from '../interfaces/survey-create.interface';

export const createSurvey = createAction(
  '[Survey Create Component] Create survey', props<{ survey: SurveyCreate }>()
);

export const addSurveyCreateSuccess = createAction(
  '[Survey Create Effect] Create survey success', props<{ survey: SurveyCreate }>()
);

export const createSurveyCreateFailure = createAction(
  '[Survey Create Effect] Create survey failure', props<{ error: any }>()
);
