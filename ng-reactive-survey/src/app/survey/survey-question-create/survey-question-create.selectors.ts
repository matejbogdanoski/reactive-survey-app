import { createSelector } from '@ngrx/store';
import { selectSurveyState } from '../reducers';
import { surveyQuestionCreateAdapter } from './survey-question-create.reducer';

//Create default selectors
export const getSurveyQuestionState = createSelector(selectSurveyState, s => s.surveyQuestionCreate);

export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal
} = surveyQuestionCreateAdapter.getSelectors(getSurveyQuestionState);
