import { Action, createReducer, on } from '@ngrx/store';
import { initialState, SurveyState } from './survey.state';
import {
  createSurveyFailure,
  createSurveySuccess,
  editSurveyCreateFailure,
  editSurveySuccess,
  findSurveyFailure,
  findSurveySuccess
} from '../services/survey/survey.actions';
import {
  addSurveyQuestionFailure,
  addSurveyQuestionSuccess,
  deleteSurveyQuestionFailure,
  deleteSurveyQuestionSuccess,
  duplicateSurveyQuestionFailure,
  duplicateSurveyQuestionSuccess,
  editSurveyQuestionFailure,
  editSurveyQuestionSuccess,
  updateSurveyQuestionPositionFailure,
  updateSurveyQuestionPositionSuccess
} from '../services/survey-question/survey-question.actions';

export const surveyModuleKey = 'survey';

export const reducer = createReducer(
  initialState,
  on(createSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(createSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(findSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(findSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(editSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(editSurveyCreateFailure, (state, action) => ({ ...state, error: action.error })),

  on(addSurveyQuestionSuccess, (state, action) => ({
    ...state,
    survey: { ...state.survey, questions: [...state.survey.questions, action.surveyQuestion] }
  })),
  on(addSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  on(deleteSurveyQuestionSuccess, (state, action) => ({
    ...state,
    survey: { ...state.survey, questions: state.survey.questions.filter(it => it.id != action.surveyQuestionId) }
  })),
  on(deleteSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  on(editSurveyQuestionSuccess,
    (state, action) => ({
      ...state,
      survey: {
        ...state.survey,
        questions: state.survey.questions.map(it => {
          const surveyQuestion = action.surveyQuestion;
          if (it.id == surveyQuestion.id) {
            return surveyQuestion;
          } else {
            return it;
          }
        })
      }
    })),
  on(editSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  on(updateSurveyQuestionPositionSuccess, ((state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: action.surveyQuestions
    }
  }))),
  on(updateSurveyQuestionPositionFailure, (state, action) => ({ ...state, error: action.error })),

  on(duplicateSurveyQuestionSuccess, (state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: action.surveyQuestions
    }
  })),
  on(duplicateSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error }))
);

export function surveyReducer(state: SurveyState | undefined, action: Action) {
  return reducer(state, action);
}

