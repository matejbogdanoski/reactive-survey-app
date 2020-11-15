import { Survey } from '../../interfaces/survey.interface';
import { SurveyInstance } from '../../interfaces/survey-instance.interface';

export interface SurveyState {
  survey: Survey;
  instances: SurveyInstance[];
  error: any;
}

export const initialState: SurveyState = {
  survey: {} as Survey,
  instances: [] as SurveyInstance[],
  error: undefined
};
