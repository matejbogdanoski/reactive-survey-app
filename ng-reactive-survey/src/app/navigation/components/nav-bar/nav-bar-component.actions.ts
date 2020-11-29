import { createAction } from '@ngrx/store';

const source = `[Navigation]`;

export const initUserFromCookie = createAction(`${source} Initializing user from cookie!`);
export const logoutUser = createAction(`${source} Logging out user!`);
