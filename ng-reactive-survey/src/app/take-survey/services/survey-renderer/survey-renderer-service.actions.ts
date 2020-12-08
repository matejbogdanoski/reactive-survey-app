import { createAction, props } from '@ngrx/store';
import { Survey } from '../../../interfaces/survey.interface';

const source = '[Survey Renderer API]';

export const findFullSurveySuccess = createAction(`${source} Find full survey success!`, props<{ survey: Survey }>());
export const findFullSurveyFailure = createAction(`${source} Find full survey failure!`, props<{ error: any }>());
