import { Action, createReducer, on } from '@ngrx/store';
import { initialState } from './security.state';
import {
  initUserFromCookieFailure,
  initUserFromCookieSuccess,
  loginFailure,
  loginSuccess,
  logoutFailure,
  logoutSuccess
} from '../services/authentication-service.actions';

export const securityModuleKey = 'security';

export const reducer = createReducer(
  initialState,
  on(loginSuccess, (state, action) => ({
    ...state,
    username: action.username,
    token: action.token
  })),
  on(loginFailure, (state, action) => ({
    ...state,
    error: action.error
  })),
  on(initUserFromCookieSuccess, (state, action) => ({
    ...state,
    token: action.token,
    username: action.username
  })),
  on(initUserFromCookieFailure, (state, action) => ({
    ...state,
    error: action.error
  })),
  on(logoutSuccess, (state, _) => ({
    ...state,
    username: undefined,
    token: undefined
  })),
  on(logoutFailure, (state, action) => ({
    ...state,
    error: action.error
  }))
);

export function securityReducer(state: any, action: Action) {
  return reducer(state, action);
}