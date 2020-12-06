import { createAction, props } from '@ngrx/store';
import { SurveyInvitation } from '../../interfaces/survey-invitation.interface';

const source = '[Survey Invitations API]';

export const findSurveyInvitationsSuccess = createAction(`${source} Find survey invitations success!`,
  props<{ surveyInvitations: SurveyInvitation[] }>());
export const findSurveyInvitationsFailure = createAction(`${source} Find survey invitations failure!`, props<{ error: any }>());

export const createSurveyInvitationSuccess = createAction(`${source} Create survey invitation success!`);
export const createSurveyInvitationFailure = createAction(`${source} Create survey invitation failure!`, props<{ error: any }>());
