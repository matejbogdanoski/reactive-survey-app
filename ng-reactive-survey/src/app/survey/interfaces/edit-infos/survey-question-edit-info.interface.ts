import { QuestionType } from '../../enum/question-type.enum';
import { SurveyQuestionOption } from '../survey-question-option.interface';

export interface SurveyQuestionEditInfo {
  questionType: QuestionType;
  name: string;
  isRequired: boolean;
}
