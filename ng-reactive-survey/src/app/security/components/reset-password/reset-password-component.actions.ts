import { createAction, props } from '@ngrx/store';
import { UpdatePasswordRequest } from '../../interfaces/request/update-password.request';

const source = '[Reset Password Component]';

export const updatePassword = createAction(`${source} Update password`, props<{ updatePasswordRequest: UpdatePasswordRequest }>());
