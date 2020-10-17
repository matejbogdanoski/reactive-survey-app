import { createFeatureSelector } from '@ngrx/store';
import { surveyQuestionCreateReducer } from './survey-question-create/survey-question-create.reducer';
import { SurveyState } from './survey-state';

export const reducers = {
  surveyQuestionCreate: surveyQuestionCreateReducer
};

export const selectSurveyState = createFeatureSelector<SurveyState>('survey');
