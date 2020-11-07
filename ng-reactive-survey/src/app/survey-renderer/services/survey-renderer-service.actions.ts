import { createAction, props } from '@ngrx/store';
import { Survey } from '../../interfaces/survey.interface';

export const source = '[Survey Renderer API]';

export const findFullSurveySuccess = createAction(`${source}`, props<{ survey: Survey }>());
export const findFullSurveyFailure = createAction(`${source}`, props<{ error: any }>());
