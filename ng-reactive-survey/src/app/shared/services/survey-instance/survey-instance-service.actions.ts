import { createAction, props } from '@ngrx/store';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';

const source = '[Survey Instance API]';

export const findSurveyInstancesSuccess = createAction(`${source} Find Survey Instances Success`,
  props<{ surveyInstances: SurveyInstance[] }>());

export const findSurveyInstancesFailure = createAction(`${source} Find  Survey Instances Failure`, props<{ error: any }>());
