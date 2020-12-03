import { createAction, props } from '@ngrx/store';
import { UpdateUserInfoRequest } from '../../interfaces/request/update-user-info.request';

const source = '[Edit Profile Component]';

export const findUserInfo = createAction(`${source} Find user info`);

export const updateUserInfo = createAction(`${source} Update user info`,
  props<{ updateUserInfoRequest: UpdateUserInfoRequest, userId: number }>());
