import { Action } from '@ngrx/store';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';

export const CREATE = '[Survey Question] Create';
export const UPDATE = '[Survey Question] Update';
export const DELETE = '[Survey Question] Delete';

export class Create implements Action {
  readonly type = CREATE;

  constructor(public surveyQuestion: SurveyQuestionCreate) {
  }
}

export class Update implements Action {
  readonly type = UPDATE;

  constructor(
    public id: number,
    public changes: Partial<SurveyQuestionCreate>
  ) {
  }
}

export class Delete implements Action {
  readonly type = DELETE;

  constructor(public id: number) {
  }
}

export type SurveyQuestionCreateActions = Create | Update | Delete;
