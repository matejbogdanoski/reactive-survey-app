import { createAction, props } from '@ngrx/store';

const source = '[Survey Responses Component]';

export const findSurveyInstances = createAction(`${source} Find survey instances`, props<{ surveyId: number }>());
