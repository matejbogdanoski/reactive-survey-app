import { QuestionType } from '../enum/question-type.enum';
import { SurveyQuestionOption } from './survey-question-option.interface';

export interface SurveyQuestion {
  id: number;
  questionType: QuestionType;
  name?: string;
  options?: SurveyQuestionOption[];
  position: number;
  isRequired: boolean;
}
