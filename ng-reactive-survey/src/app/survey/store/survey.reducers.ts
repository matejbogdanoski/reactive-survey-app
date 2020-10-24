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
import {
  addSurveyQuestionOptionFailure,
  addSurveyQuestionOptionSuccess,
  deleteSurveyQuestionOptionFailure,
  deleteSurveyQuestionOptionSuccess,
  updateQuestionOptionLabelFailure,
  updateQuestionOptionLabelSuccess,
  updateQuestionOptionPositionFailure,
  updateQuestionOptionPositionSuccess
} from '../services/survey-question-option/survey-question-option.actions';
import { SurveyQuestion } from '../interfaces/survey-question.interface';
import { TypedAction } from '@ngrx/store/src/models';

export const surveyModuleKey = 'survey';

export const reducer = createReducer(
  initialState,
  //Survey Reducers
  on(createSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(createSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(findSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(findSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(editSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(editSurveyCreateFailure, (state, action) => ({ ...state, error: action.error })),

  //Survey Question Reducers
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
  on(duplicateSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  //Survey Question Options Reducers
  on(addSurveyQuestionOptionSuccess, (state, action) => ({
    ...state,
    survey: replaceSurveyQuestion(state, action)
  })),
  on(addSurveyQuestionOptionFailure, (state, action) => ({ ...state, error: action.error })),

  on(deleteSurveyQuestionOptionSuccess, (state, action) => ({
    ...state,
    survey: replaceSurveyQuestion(state, action)
  })),
  on(deleteSurveyQuestionOptionFailure, (state, action) => ({ ...state, error: action.error })),

  on(updateQuestionOptionPositionSuccess, (state, action) => ({
    ...state,
    survey: replaceSurveyQuestion(state, action)
  })),
  on(updateQuestionOptionPositionFailure, (state, action) => ({ ...state, error: action.error })),

  on(updateQuestionOptionLabelSuccess, (state, action) => ({
    ...state,
    survey: replaceSurveyQuestion(state, action)
  })),
  on(updateQuestionOptionLabelFailure, (state, action) => ({ ...state, error: action.error }))
);

export function surveyReducer(state: SurveyState | undefined, action: Action) {
  return reducer(state, action);
}

function replaceSurveyQuestion(state: SurveyState, action: { surveyQuestion: SurveyQuestion } & TypedAction<string> & { type: string }) {
  return {
    ...state.survey,
    questions: state.survey.questions.map(q => {
      if (q.id === action.surveyQuestion.id) {
        return action.surveyQuestion;
      } else {
        return q;
      }
    })
  };
}
