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
  findSurveyQuestionsFailure,
  findSurveyQuestionsSuccess,
  updateSurveyQuestionPositionFailure,
  updateSurveyQuestionPositionSuccess
} from '../services/survey-question/survey-question.actions';
import {
  addSurveyQuestionOptionFailure,
  addSurveyQuestionOptionSuccess,
  deleteSurveyQuestionOptionFailure,
  deleteSurveyQuestionOptionSuccess,
  findSurveyQuestionOptionsFailure,
  findSurveyQuestionOptionsSuccess,
  updateQuestionOptionLabelFailure,
  updateQuestionOptionLabelSuccess,
  updateQuestionOptionPositionFailure,
  updateQuestionOptionPositionSuccess
} from '../services/survey-question-option/survey-question-option.actions';
import {
  findInstancePreviewFailure,
  findInstancePreviewSuccess,
  findSurveyInstancesSuccess
} from '../../shared/services/survey-instance/survey-instance-service.actions';
import { aggregateAnswer } from '../components/survey-responses/survey-responses-component.actions';
import { SurveyInstance } from '../../interfaces/survey-instance.interface';
import { AnswerDTO } from '../../shared/services/survey-instance/survey-instance.service';
import * as _ from 'lodash';
import { QuestionAnswer } from '../../interfaces/question-answer.interface';
import {
  findSurveyInvitationsFailure,
  findSurveyInvitationsSuccess
} from '../services/survey-invitations/survey-invitations-service.actions';

export const surveyModuleKey = 'survey';

export const reducer = createReducer(
  initialState,
  //Survey Reducers
  on(createSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(createSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(findSurveySuccess, (state, action) => ({ ...state, survey: action.survey })),
  on(findSurveyFailure, (state, action) => ({ ...state, error: action.error })),

  on(editSurveySuccess, (state, action) => (
    {
      ...state,
      survey: {
        ...state.survey,
        title: action.survey.title,
        description: action.survey.description
      }
    })),
  on(editSurveyCreateFailure, (state, action) => ({ ...state, error: action.error })),

  //Survey Question Reducers
  on(addSurveyQuestionSuccess, (state, action) => ({
    ...state,
    survey: { ...state.survey, questions: [...state.survey.questions || [], action.surveyQuestion] }
  })),
  on(addSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  on(findSurveyQuestionsSuccess, (state, action) => ({
    ...state,
    survey: { ...state.survey, questions: action.surveyQuestions }
  })),
  on(findSurveyQuestionsFailure, (state, action) => ({ ...state, error: action.error })),

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
            return {
              ...surveyQuestion,
              options: it.options
            };
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
      questions: [...state.survey.questions, action.surveyQuestion].sort((a, b) => a.position - b.position || b.id - a.id)
    }
  })),
  on(duplicateSurveyQuestionFailure, (state, action) => ({ ...state, error: action.error })),

  //Survey Question Options Reducers
  on(findSurveyQuestionOptionsSuccess, (state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: state.survey.questions.map(q => {
        if (q.id === action.surveyQuestionId) {
          return { ...q, options: action.questionOptions };
        } else {
          return q;
        }
      })
    }
  })),
  on(findSurveyQuestionOptionsFailure, (state, action) => ({ ...state, error: action.error })),

  on(addSurveyQuestionOptionSuccess, (state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: state.survey.questions.map(q => {
        if (action.surveyQuestion.id === q.id) {
          return {
            ...q,
            options: [...q.options || [], action.questionOption]
          };
        } else {
          return q;
        }
      })
    }
  })),
  on(addSurveyQuestionOptionFailure, (state, action) => ({ ...state, error: action.error })),

  on(deleteSurveyQuestionOptionSuccess, (state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: state.survey.questions.map(q => {
        if (action.surveyQuestion.id === q.id) {
          return { ...q, options: q.options.filter(o => o.id !== action.questionOptionId) };
        } else {
          return q;
        }
      })
    }
  })),
  on(deleteSurveyQuestionOptionFailure, (state, action) => ({ ...state, error: action.error })),

  on(updateQuestionOptionPositionSuccess, (state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: state.survey.questions.map(q => {
        if (action.surveyQuestion.id === q.id) {
          return { ...q, options: action.questionOptions };
        } else {
          return q;
        }
      })
    }
  })),
  on(updateQuestionOptionPositionFailure, (state, action) => ({ ...state, error: action.error })),

  on(updateQuestionOptionLabelSuccess, (state, action) => ({
    ...state,
    survey: {
      ...state.survey,
      questions: state.survey.questions.map(q => {
        if (action.surveyQuestion.id === q.id) {
          return {
            ...q, options: q.options.map(o => {
              if (o.id == action.questionOption.id) {
                return action.questionOption;
              } else {
                return o;
              }
            })
          };
        } else {
          return q;
        }
      })
    }
  })),
  on(updateQuestionOptionLabelFailure, (state, action) => ({ ...state, error: action.error })),

  //Survey Instances
  on(findSurveyInstancesSuccess, (state, action) => ({
    ...state,
    instances: action.surveyInstances
  })),
  on(updateQuestionOptionLabelFailure, (state, action) => ({ ...state, error: action.error })),

  on(aggregateAnswer, (state, action) => ({
    ...state,
    instances: aggregateInstance(state.instances, action.answer)
  })),
  on(findInstancePreviewSuccess, (state, action) => ({
    ...state,
    singlePreviewInstance: action.surveyInstancePreview
  })),
  on(findInstancePreviewFailure, (state, action) => ({ ...state, error: action.error })),
  //Survey invitations
  on(findSurveyInvitationsSuccess, ((state, action) => ({
    ...state,
    surveyInvitations: action.surveyInvitations
  }))),
  on(findSurveyInvitationsFailure, (state, action) => ({ ...state, error: action.error }))
);

export function surveyReducer(state: SurveyState | undefined, action: Action) {
  return reducer(state, action);
}

function aggregateInstance(instances: SurveyInstance[], answer: AnswerDTO): SurveyInstance[] {
  const instancesCopy = _.cloneDeep(instances);
  const instance = instancesCopy.find(i => i.id === answer.surveyInstanceId);
  if (!!instance) {
    return instancesCopy.map(i => {
      if (i.id == instance.id) {
        return {
          ...i,
          questionAnswers: [...i.questionAnswers, {
            questionId: answer.surveyQuestionId,
            answer: answer.answer,
            questionName: answer.questionName,
            questionType: answer.questionType
          } as QuestionAnswer]
        };
      } else {
        return i;
      }
    });
  } else {
    return [...instancesCopy, {
      questionAnswers: [{
                          questionId: answer.surveyQuestionId,
                          questionType: answer.questionType,
                          questionName: answer.questionName,
                          answer: answer.answer
                        } as QuestionAnswer],
      dateTaken: new Date(),
      id: answer.surveyInstanceId
    } as SurveyInstance];
  }
}
