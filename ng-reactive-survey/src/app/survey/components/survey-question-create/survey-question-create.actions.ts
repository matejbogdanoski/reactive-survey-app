import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

const source = '[Survey Question Create Component]';

export const addSurveyQuestion = createAction(`${source} Add Survey Question`);

export const editSurveyQuestion = createAction(`${source} Update`, props<{ id: number, changes: Partial<SurveyQuestion> }>());

export const deleteSurveyQuestion = createAction(`${source} Delete`, props<{ id: number }>());

export const updateSurveyQuestionPosition = createAction(`${source} Change position`,
  props<{ id: number, previousIndex: number, currentIndex: number }>());

export const duplicateSurveyQuestion = createAction(`${source} Duplicate Survey Question`, props<{ question: SurveyQuestion }>());
