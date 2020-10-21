import { Survey } from '../interfaces/survey.interface';

export interface SurveyState {
  survey: Survey;
  error: any;
}

export const initialState: SurveyState = {
  survey: {} as Survey,
  error: undefined
};
