import * as actions from './survey-question-create.actions';
import { SurveyQuestionCreateState } from './survey-question-create.state';
import { surveyQuestionCreateAdapter } from './survey-question-create.adapters';
import { QuestionType } from '../enum/question-type.enum';
import { Survey } from '../interfaces/survey.interface';
import { QuestionOption } from '../interfaces/question-option.interface';

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

export const initialState: SurveyQuestionCreateState = surveyQuestionCreateAdapter.getInitialState(defaultSurvey);

//Reducer
export function surveyQuestionCreateReducer(
  state: SurveyQuestionCreateState = initialState,
  action: actions.SurveyQuestionCreateActions
) {

  switch (action.type) {

    case actions.CREATE:
      return surveyQuestionCreateAdapter.addOne(action.surveyQuestion, state);

    case actions.UPDATE:
      return surveyQuestionCreateAdapter.updateOne({
        id: action.id,
        changes: action.changes
      }, state);

    case actions.DELETE:
      return surveyQuestionCreateAdapter.removeOne(action.id, state);

    default:
      return state;
  }

}
