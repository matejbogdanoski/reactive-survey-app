import { SurveyQuestion } from '../interfaces/survey-question.interface';
import { Survey } from '../interfaces/survey.interface';

export interface SurveyState {
  survey: Survey;
  questions: SurveyQuestion[];
  error: any;
}
