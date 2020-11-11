import { createFeatureSelector, createSelector } from '@ngrx/store';
import { surveyRendererModuleKey } from './take-survey.reducers';
import { TakeSurveyState } from './take-survey.state';

export const selectSurveyRendererState = createFeatureSelector<TakeSurveyState>(surveyRendererModuleKey);

export const selectFullSurvey = createSelector(selectSurveyRendererState, s => s.survey);
