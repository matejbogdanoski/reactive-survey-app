import { Observable, of } from 'rxjs';

export enum QuestionType {
  SHORT_TEXT = 'SHORT_TEXT',
  LONG_TEXT = 'LONG_TEXT',
  MULTIPLE_CHOICE = 'MULTIPLE_CHOICE',
  CHECKBOX = 'CHECKBOX',
  DROPDOWN = 'DROPDOWN',
  DATE = 'DATE',
  TIME = 'TIME'
}

export function getQuestionTypes(): Observable<QuestionType[]> {
  return of(Object.values(QuestionType));
}

export function hasOptions(type: QuestionType): Observable<boolean> {
  return of([
    QuestionType.MULTIPLE_CHOICE,
    QuestionType.CHECKBOX,
    QuestionType.DROPDOWN
  ].includes(type));
}

export function hasOptionsStatic(type: QuestionType): boolean {
  return [
    QuestionType.MULTIPLE_CHOICE,
    QuestionType.CHECKBOX,
    QuestionType.DROPDOWN
  ].includes(type);
}
