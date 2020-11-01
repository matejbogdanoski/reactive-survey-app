import { createAction, props } from '@ngrx/store';
import { SurveyEditInfo } from '../../interfaces/edit-infos/survey-edit-info.interface';

const source = '[Survey Edit Component]';

export const editSurvey = createAction(`${source} Edit survey`, props<{ surveyEditInfo: Partial<SurveyEditInfo> }>());

export const findSurveyQuestions = createAction(`${source} Find Survey Questions`, props<{ surveyId: number }>());
