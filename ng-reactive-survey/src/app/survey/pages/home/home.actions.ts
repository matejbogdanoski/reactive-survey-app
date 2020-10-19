import { createAction, props } from '@ngrx/store';
import { Survey } from '../../interfaces/survey.interface';

const source = '[Home Page]';
const effectSource = '[Home Effect]';

export const createSurvey = createAction(
  `${source} Create survey`
);

export const createSurveySuccess = createAction(
  `${effectSource} Create survey success`, props<{ survey: Survey }>()
);

export const createSurveyFailure = createAction(
  `${effectSource} Create survey failure`, props<{ error: any }>()
);
