import { QuestionType } from '../../enum/question-type.enum';

export interface SurveyQuestionEditInfo {
  questionType: QuestionType;
  name: string;
  isRequired: boolean;
}
