import { Survey } from '../../interfaces/survey.interface';

export interface TakeSurveyState {
  survey: Survey;
  error: any;
}

export const initialState: TakeSurveyState = {
  survey: {} as Survey,
  error: undefined
};
