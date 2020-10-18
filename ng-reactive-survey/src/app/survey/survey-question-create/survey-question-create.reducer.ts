import { SurveyQuestionCreateState } from './survey-question-create.state';
import { QuestionType } from '../enum/question-type.enum';
import { QuestionOption } from '../interfaces/question-option.interface';
import { createReducer, on } from '@ngrx/store';
import * as SurveyQuestionCreateActions from './survey-question-create.actions';
import { createEntityAdapter } from '@ngrx/entity';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';

//Default data / initial state
const defaultSurvey = {
  ids: [1],
  entities: {
    1: {
      id: 1,
      isRequired: false,
      name: 'Untitled question',
      options: [{ id: 1, label: 'Option 1' } as QuestionOption],
      questionType: QuestionType.MULTIPLE_CHOICE,
      position: 1
    }
  }
};

export const surveyQuestionCreateAdapter = createEntityAdapter<SurveyQuestionCreate>();

export const initialState: SurveyQuestionCreateState = surveyQuestionCreateAdapter.getInitialState(defaultSurvey);

//Reducer
export const surveyQuestionCreateReducer = createReducer(
  initialState,
  on(SurveyQuestionCreateActions.addSurveyQuestion,
    (state, action) => surveyQuestionCreateAdapter.addOne(action.surveyQuestion, state)
  ),
  on(SurveyQuestionCreateActions.deleteSurveyQuestion, (state, action) => surveyQuestionCreateAdapter.removeOne(action.id, state)),
  on(SurveyQuestionCreateActions.updateSurveyQuestion,
    (state, action) => surveyQuestionCreateAdapter.updateOne({
      id: action.id,
      changes: action.changes
    }, state)
  )
);
