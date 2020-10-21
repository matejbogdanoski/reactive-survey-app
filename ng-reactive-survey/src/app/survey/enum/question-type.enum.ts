import { Observable, of } from 'rxjs';

export enum QuestionType {
  SHORT_TEXT = 'SHORT_TEXT',
  LONG_TEXT = 'LONG_TEXT',
  MULTIPLE_CHOICE = 'MULTIPLE_CHOICE',
  CHECKBOX = 'CHECKBOX',
  DROPDOWN = 'DROPDOWN',
  LINEAR_SCALE = 'LINEAR_SCALE',
  DATE = 'DATE',
  TIME = 'TIME'
}

export function getQuestionTypes(): Observable<QuestionType[]> {
  return of(Object.values(QuestionType));
}
