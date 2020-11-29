import { createAction } from '@ngrx/store';

const source = `[Guard]`;

export const navigateToLogin = createAction(`${source} Navigate to login!`);
