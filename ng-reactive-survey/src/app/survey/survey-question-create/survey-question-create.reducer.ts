import { SurveyQuestionCreateState } from './survey-question-create.state';
import { createReducer, on } from '@ngrx/store';
import { addSurveyQuestion, addSurveyQuestionFailure, deleteSurveyQuestion, updateSurveyQuestion } from './survey-question-create.actions';
import { update } from '../../helpers/helper-functions';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';
import { QuestionType } from '../enum/question-type.enum';
import { QuestionOption } from '../interfaces/question-option.interface';

export const initialState: SurveyQuestionCreateState = {
  questions: [{
                name: 'Untitled question',
                isRequired: false,
                options: [{ id: 1, label: 'Option 1' } as QuestionOption],
                position: 1,
                questionType: QuestionType.MULTIPLE_CHOICE
              } as SurveyQuestionCreate],
  error: undefined
};

export const surveyQuestionCreateReducer = createReducer(
  initialState,
  on(addSurveyQuestion,
    (state, action) => ({
      ...state,
      questions: [...state.questions, action.surveyQuestion]
    })
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
