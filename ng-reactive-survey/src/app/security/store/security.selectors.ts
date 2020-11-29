import { createFeatureSelector, createSelector } from '@ngrx/store';
import { securityModuleKey } from './security.reducers';
import { SecurityState } from './security.state';

export const selectSecurityState = createFeatureSelector<SecurityState>(securityModuleKey);

export const selectToken = createSelector(selectSecurityState, s => s?.token);
export const selectUsername = createSelector(selectSecurityState, s => s?.username);
