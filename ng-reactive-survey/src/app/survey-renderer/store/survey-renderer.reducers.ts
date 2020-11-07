import { Action, createReducer, on } from '@ngrx/store';
import { initialState, SurveyRendererState } from './survey-renderer.state';
import { findFullSurveyFailure, findFullSurveySuccess } from '../services/survey-renderer-service.actions';

export const surveyRendererModuleKey = 'survey-renderer';

export const reducer = createReducer(
  initialState,
  on(findFullSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(findFullSurveyFailure, (state, action) => ({ ...state, error: action.error }))
);

export function surveyRendererReducer(state: SurveyRendererState | undefined, action: Action) {
  return reducer(state, action);
}
