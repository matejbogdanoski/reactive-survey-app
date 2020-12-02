import { createAction, props } from '@ngrx/store';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';
import { SurveyInstancePreview } from '../../../interfaces/survey-instance-preview.interface';

const source = '[Survey Instance API]';

export const findSurveyInstancesSuccess = createAction(`${source} Find Survey Instances Success`,
  props<{ surveyInstances: SurveyInstance[] }>());

export const findSurveyInstancesFailure = createAction(`${source} Find Survey Instances Failure`, props<{ error: any }>());

export const findInstancePreviewSuccess = createAction(`${source} Find Instance Preview Success`,
  props<{ surveyInstancePreview: SurveyInstancePreview }>());
export const findInstancePreviewFailure = createAction(`${source} Find Instance Preview Failure`, props<{ error: any }>());
