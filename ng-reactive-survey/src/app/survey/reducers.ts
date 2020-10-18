import { createFeatureSelector } from '@ngrx/store';
import { surveyQuestionCreateReducer } from './survey-question-create/survey-question-create.reducer';
import { SurveyState } from './survey-state';
import { surveyCreateReducer } from './survey-create/survey-create.reducer';

export const reducers = {
  surveyCreate: surveyCreateReducer,
  surveyQuestionCreate: surveyQuestionCreateReducer
};

export const selectSurveyState = createFeatureSelector<SurveyState>('survey');
