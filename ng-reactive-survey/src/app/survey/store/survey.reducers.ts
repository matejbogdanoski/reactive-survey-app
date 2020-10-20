import { Action, createReducer, on } from '@ngrx/store';
import { initialState, SurveyState } from './survey.state';
import { deleteSurveyQuestion, updateSurveyQuestion } from '../components/survey-question-create/survey-question-create.actions';
import { update } from '../../helpers/helper-functions';
import {
  createSurveyFailure,
  createSurveySuccess,
  editSurveyCreateFailure, editSurveySuccess,
  findSurveyFailure,
  findSurveySuccess
} from '../services/survey/survey.actions';
import { addSurveyQuestionFailure, addSurveyQuestionSuccess } from '../services/survey-question/survey-question.actions';

export const surveyModuleKey = 'survey';

export const reducer = createReducer(
  initialState,
  on(createSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(createSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(findSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(findSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(editSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(editSurveyCreateFailure, (state, action) => ({ ...state, error: action.error })),

  on(addSurveyQuestionSuccess, (state, action) => ({ ...state, questions: [...state.questions, action.surveyQuestion] })),
  on(addSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  on(deleteSurveyQuestion, (state, action) => ({ ...state, questions: state.questions.filter(it => it != action.surveyQuestion) })),
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
    }))
);

export function surveyReducer(state: SurveyState | undefined, action: Action) {
  return reducer(state, action);
}

