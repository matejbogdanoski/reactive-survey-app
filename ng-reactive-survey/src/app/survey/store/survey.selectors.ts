import { createFeatureSelector, createSelector } from '@ngrx/store';
import { SurveyState } from './survey.state';
import { surveyModuleKey } from './survey.reducers';

export const selectSurveyState = createFeatureSelector<SurveyState>(surveyModuleKey);

export const selectSurvey = createSelector(selectSurveyState, s => s.survey);

export const selectSurveyQuestions = createSelector(selectSurveyState, s => s.survey.questions);

export const selectSurveyInstances = createSelector(selectSurveyState, s => s.instances);

export const selectSinglePreviewInstance = createSelector(selectSurveyState, s => s.singlePreviewInstance);
