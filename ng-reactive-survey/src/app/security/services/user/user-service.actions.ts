import { createAction, props } from '@ngrx/store';
import { UserInfo } from '../../interfaces/user-info.interface';

const source = `[Users API]`;

export const registerUserSuccess = createAction(`${source} Register user success`);
export const registerUserFailure = createAction(`${source} Register user failure`, props<{ error: any }>());

export const findUserInfoSuccess = createAction(`${source} Find user info success`, props<{ userInfo: UserInfo }>());
export const findUserInfoFailure = createAction(`${source} Find user info failure`, props<{ error: any }>());

export const updateUserInfoSuccess = createAction(`${source} Update user info success`, props<{ userInfo: UserInfo }>());
export const updateUserInfoFailure = createAction(`${source} Update user info failure`, props<{ error: any }>());
