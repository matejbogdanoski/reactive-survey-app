import { QuestionType } from '../enum/question-type.enum';
import { QuestionOption } from './question-option.interface';

export interface SurveyQuestion {
  id: number;
  questionType: QuestionType;
  name?: string;
  options?: QuestionOption[];
  position: number;
  isRequired: boolean;
}
