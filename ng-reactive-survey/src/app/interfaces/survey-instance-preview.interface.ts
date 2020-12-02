import { Survey } from './survey.interface';
import { SurveyInstance } from './survey-instance.interface';

export interface SurveyInstancePreview {
  survey: Survey;
  surveyInstance: SurveyInstance;
}
