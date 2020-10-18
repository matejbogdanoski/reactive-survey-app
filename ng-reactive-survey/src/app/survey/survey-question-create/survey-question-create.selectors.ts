import { createSelector } from '@ngrx/store';
import { selectSurveyState } from '../reducers';

//Create default selectors
export const selectSurveyQuestionState = createSelector(selectSurveyState, s => s.surveyQuestionCreate);

export const selectSurveyQuestions = createSelector(selectSurveyQuestionState, s => s.questions);
