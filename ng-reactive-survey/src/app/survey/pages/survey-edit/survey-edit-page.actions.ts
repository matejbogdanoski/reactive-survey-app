import { createAction, props } from '@ngrx/store';

const source = '[Survey Edit Page]';

export const findSurvey = createAction(`${source} Find Survey`, props<{ id: number }>());

export const previewSurvey = createAction(`${source} Preview survey`);
