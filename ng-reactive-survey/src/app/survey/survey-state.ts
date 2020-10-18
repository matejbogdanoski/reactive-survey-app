import { SurveyQuestionCreateState } from './survey-question-create/survey-question-create.state';
import { SurveyCreateState } from './survey-create/survey-create.state';

export interface SurveyState {
  surveyCreate: SurveyCreateState;
  surveyQuestionCreate: SurveyQuestionCreateState;
}
