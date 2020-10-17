import { createEntityAdapter } from '@ngrx/entity';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';

//Entity adapter
export const surveyQuestionCreateAdapter = createEntityAdapter<SurveyQuestionCreate>();
