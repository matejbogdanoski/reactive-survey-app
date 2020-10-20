import { createAction } from '@ngrx/store';

const source = '[Home Page]';

export const createSurvey = createAction(`${source} Create survey`);
