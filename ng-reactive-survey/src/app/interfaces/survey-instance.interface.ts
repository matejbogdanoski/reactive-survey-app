import { QuestionAnswer } from './question-answer.interface';

export interface SurveyInstance {
  id: number;
  dateTaken: Date;
  questionAnswers: QuestionAnswer[];
}
