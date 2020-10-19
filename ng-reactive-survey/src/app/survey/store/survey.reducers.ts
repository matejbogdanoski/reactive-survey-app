import { Action, createReducer, on } from '@ngrx/store';
import { editSurvey, editSurveyCreateFailure } from '../components/survey-create/survey-create.actions';
import { Survey } from '../interfaces/survey.interface';
import { SurveyState } from './survey.state';
import {
  addSurveyQuestionFailure,
  addSurveyQuestionSuccess,
  deleteSurveyQuestion,
  updateSurveyQuestion
} from '../components/survey-question-create/survey-question-create.actions';
import { update } from '../../helpers/helper-functions';
import { createSurveySuccess } from '../pages/home/home.actions';
import { findSurveySuccess } from '../pages/survey-edit/survey-edit-page.actions';

export const initialState: SurveyState = {
  questions: [],
  survey: {} as Survey,
  error: undefined
};

export const reducer = createReducer(
  initialState,
  on(createSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),

  on(findSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),

  on(editSurvey, (state, action) => ({ ...state, survey: action.survey })),
  on(editSurveyCreateFailure,
    (state, action) => {
      return {
        ...state,
        error: action.error
      };
    }),

  on(addSurveyQuestionSuccess,
    (state, action) => ({ ...state, questions: [...state.questions, action.surveyQuestion] })
  ),
  on(addSurveyQuestionFailure,
    (state, action) => {
      return {
        ...state,
        error: action.error
      };
    }
  ),

  on(deleteSurveyQuestion, (state, action) => ({
    ...state,
    questions: state.questions.filter(it => it != action.surveyQuestion)
  })),
  on(updateSurveyQuestion,
    (state, action) => ({
      ...state,
      questions: state.questions.map(it => {
        if (it == action.surveyQuestion) {
          return update(it, action.changes);
        } else {
          return it;
        }
      })
    })
  )
);

export function surveyReducer(state: SurveyState | undefined, action: Action) {
  return reducer(state, action);
}

