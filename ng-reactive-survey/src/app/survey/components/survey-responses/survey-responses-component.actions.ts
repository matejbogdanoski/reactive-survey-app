import { createAction, props } from '@ngrx/store';
import { AnswerDTO } from '../../../shared/services/survey-instance/survey-instance.service';

const source = '[Survey Responses Component]';

export const findSurveyInstances = createAction(`${source} Find survey instances`, props<{ surveyId: number }>());

export const aggregateAnswer = createAction(`${source} Aggregate answer`, props<{ answer: AnswerDTO }>());
