import { createSelector } from '@ngrx/store';
import { selectSurveyState } from '../reducers';

export const selectSurveyCreateState = createSelector(selectSurveyState, s => s.surveyCreate);

export const selectSurvey = createSelector(selectSurveyCreateState, s => s.survey);
