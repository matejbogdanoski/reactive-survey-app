import { createFeatureSelector, createSelector } from '@ngrx/store';
import { SurveyState } from './survey.state';

export const selectSurveyState = createFeatureSelector<SurveyState>('survey');

export const selectSurvey = createSelector(selectSurveyState, s => s.survey);

export const selectSurveyQuestions = createSelector(selectSurveyState, s => s.questions);
