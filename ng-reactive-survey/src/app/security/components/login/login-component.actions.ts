import { createAction, props } from '@ngrx/store';

const source = `[Login Component]`;

export const loginAction = createAction(`${source} Login`, props<{ username: string, password: string }>());
