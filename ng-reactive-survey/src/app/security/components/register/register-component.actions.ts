import { createAction, props } from '@ngrx/store';
import { UserCreateRequest } from '../../interfaces/request/user-create.request';

const source = `[Register Component]`;

export const registerUser = createAction(`${source} Register user!`, props<{ request: UserCreateRequest }>());
