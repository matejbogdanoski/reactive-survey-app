import { createAction, props } from '@ngrx/store';
import { Survey } from '../../interfaces/survey.interface';

const source = '[Survey Edit Page]';
const effectSource = '[Survey Edit Effect]';

export const findSurvey = createAction(
  `${source} Find Survey`, props<{ naturalKey: string }>()
);

export const findSurveySuccess = createAction(
  `${effectSource} Find survey success`, props<{ survey: Survey }>()
);

export const findSurveyFailure = createAction(
  `${effectSource} Find survey failure`, props<{ error: any }>()
);
