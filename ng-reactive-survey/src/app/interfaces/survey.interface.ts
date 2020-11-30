import { SurveyQuestion } from './survey-question.interface';

export interface Survey {
  id: number;
  title: string;
  description?: string;
  naturalKey: string;
  questions: SurveyQuestion[];
}
