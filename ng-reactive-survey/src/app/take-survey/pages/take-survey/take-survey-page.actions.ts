import { createAction, props } from '@ngrx/store';

const source = '[Take Survey Page]';

export const findFullSurvey = createAction(`${source} Find Full Survey`, props<{ naturalKey: string }>());
export const clearFullSurvey = createAction(`${source} Clear full survey!`);
