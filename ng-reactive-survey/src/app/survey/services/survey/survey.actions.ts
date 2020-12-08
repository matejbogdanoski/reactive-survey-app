import { createAction, props } from '@ngrx/store';
import { Survey } from '../../../interfaces/survey.interface';

const source = '[Survey API]';

export const findSurveySuccess = createAction(`${source} Find survey success`, props<{ survey: Survey }>());
export const findSurveyFailure = createAction(`${source} Find survey failure`, props<{ error: any }>());

export const createSurveySuccess = createAction(`${source} Create survey success`, props<{ survey: Survey }>());
export const createSurveyFailure = createAction(`${source} Create survey failure`, props<{ error: any }>());

export const editSurveySuccess = createAction(`${source} Edit survey success`, props<{ survey: Survey }>());
export const editSurveyCreateFailure = createAction(`${source} Edit survey failure`, props<{ error: any }>());

export const previewSurveySuccess = createAction(`${source} Preview survey success!`);
export const previewSurveyFailure = createAction(`${source} Preview survey failure!`, props<{ error: any }>());
