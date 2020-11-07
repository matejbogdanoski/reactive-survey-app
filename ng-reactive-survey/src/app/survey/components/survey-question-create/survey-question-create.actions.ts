import { createAction, props } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { SurveyQuestionOption } from '../../interfaces/survey-question-option.interface';

const source = '[Survey Question Create Component]';

export const addSurveyQuestion = createAction(`${source} Add Survey Question`);

export const editSurveyQuestion = createAction(`${source} Update`, props<{ id: number, changes: Partial<SurveyQuestion> }>());

export const deleteSurveyQuestion = createAction(`${source} Delete`, props<{ id: number }>());

export const updateSurveyQuestionPosition = createAction(`${source} Change position`,
  props<{ id: number, previousIndex: number, currentIndex: number }>());

export const duplicateSurveyQuestion = createAction(`${source} Duplicate Survey Question`, props<{ question: SurveyQuestion }>());

export const findSurveyQuestionOptions = createAction(`${source} Find Survey Question Options`, props<{ surveyQuestionId: number }>());

export const addQuestionOption = createAction(`${source} Add Question Option`, props<{ surveyQuestion: SurveyQuestion }>());

export const deleteQuestionOption = createAction(`${source} Delete Question Option`, props<{
  surveyQuestion: SurveyQuestion,
  surveyQuestionOption: SurveyQuestionOption
}>());

export const updateQuestionOptionPosition = createAction(`${source} Update Question Option Position`, props<{
  surveyQuestion: SurveyQuestion,
  optionId: number,
  previousIndex: number,
  currentIndex: number
}>());

export const updateQuestionOptionLabel = createAction(`${source} Update Question Option Label`, props<{
  surveyQuestion: SurveyQuestion,
  optionId: number,
  changedLabel: string
}>());
