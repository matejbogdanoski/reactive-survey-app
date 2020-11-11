export interface QuestionAnswer {
  id: number;
  surveyQuestionId: number;
  surveyId: number;
  answer: string;
  answeredBy: number;
}
