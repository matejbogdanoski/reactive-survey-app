import { QuestionType } from '../survey/enum/question-type.enum';

export interface QuestionAnswer {
  questionId: number;
  questionName: string;
  answer: string;
  questionType: QuestionType;
}
