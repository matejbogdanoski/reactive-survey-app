import { QuestionType } from '../survey/enum/question-type.enum';

export interface QuestionAnswer {
  questionName: string;
  answer: string;
  questionType: QuestionType;
}
