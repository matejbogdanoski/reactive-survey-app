import { QuestionAnswer } from './question-answer.interface';

export interface SurveyInstance {
  dateTaken: Date;
  questionAnswers: QuestionAnswer[];
}
