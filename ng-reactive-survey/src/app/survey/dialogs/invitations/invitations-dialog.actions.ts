import { createAction, props } from '@ngrx/store';

const source = '[Invitations Dialog]';

export const findInvitations = createAction(`${source} Find invitations`, props<{ surveyId: number }>());
export const createInvitation = createAction(`${source} Create invitation`, props<{ surveyId: number, username: string }>());
