import { createFeatureSelector, createSelector } from '@ngrx/store';
import { surveyRendererModuleKey } from './survey-renderer.reducers';
import { SurveyRendererState } from './survey-renderer.state';

export const selectSurveyRendererState = createFeatureSelector<SurveyRendererState>(surveyRendererModuleKey);

export const selectFullSurvey = createSelector(selectSurveyRendererState, s => s.survey);
