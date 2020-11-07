import { Survey } from '../../interfaces/survey.interface';

export interface SurveyRendererState {
  survey: Survey;
  error: any;
}

export const initialState: SurveyRendererState = {
  survey: {} as Survey,
  error: undefined
};
