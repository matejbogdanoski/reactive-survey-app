import { SurveyCreate } from '../interfaces/survey-create.interface';
import { SurveyCreateState } from './survey-create.state';
import { createReducer, on } from '@ngrx/store';
import { createSurvey, createSurveyCreateFailure } from './survey-create.actions';

export const initialState: SurveyCreateState = {
  survey: {
    title: 'Survey Title',
    description: 'Survey Description',
    canTakeAnonymously: true
  } as SurveyCreate,
  error: undefined
};

export const surveyCreateReducer = createReducer(
  initialState,
  on(createSurvey, (state, action) => ({ survey: action.survey })),
  on(createSurveyCreateFailure,
    (state, action) => {
      return {
        ...state,
        error: action.error
      };
    })
);
