import { createSelector } from '@ngrx/store';
import { surveyQuestionCreateAdapter } from './survey-question-create.adapters';
import { selectSurveyState } from '../reducers';

//Create default selectors
export const getSurveyQuestionState = createSelector(selectSurveyState, s => s.surveyQuestionCreate);

export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal
} = surveyQuestionCreateAdapter.getSelectors(getSurveyQuestionState);
