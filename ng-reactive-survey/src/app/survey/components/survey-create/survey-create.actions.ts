import { createAction, props } from '@ngrx/store';
import { Survey } from '../../interfaces/survey.interface';

export const editSurvey = createAction(
  '[Survey Edit Component] Edit survey', props<{ survey: Survey }>()
);

export const editSurveySuccess = createAction(
  '[Survey Edit Effect] Edit survey success', props<{ survey: Survey }>()
);

export const editSurveyCreateFailure = createAction(
  '[Survey Edit Effect] Edit survey failure', props<{ error: any }>()
);
