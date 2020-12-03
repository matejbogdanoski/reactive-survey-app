import { createAction, props } from '@ngrx/store';

const source = `[Authentication API]`;

export const loginSuccess = createAction(`${source} Login success`, props<{ username: string, token: string }>());
export const loginFailure = createAction(`${source} Login failure`, props<{ error: any }>());

export const logoutSuccess = createAction(`${source} Logout success`);
export const logoutFailure = createAction(`${source} Logout failure`, props<{ error: any }>());

export const navigateToLoginSuccess = createAction(`${source} Navigate to login success`);
export const navigateToLoginFailure = createAction(`${source} Navigate to login failure`, props<{ error: any }>());

export const initUserFromCookieSuccess = createAction(`${source} Init user from cookie success`,
  props<{ username: string, token: string }>());
export const initUserFromCookieFailure = createAction(`${source} Init user from cookie failure`, props<{ error: any }>());
