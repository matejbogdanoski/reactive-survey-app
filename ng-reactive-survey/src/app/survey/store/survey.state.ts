import { Survey } from '../../interfaces/survey.interface';
import { SurveyInstance } from '../../interfaces/survey-instance.interface';
import { SurveyInstancePreview } from '../../interfaces/survey-instance-preview.interface';

export interface SurveyState {
  survey: Survey;
  instances: SurveyInstance[];
  singlePreviewInstance: SurveyInstancePreview;
  error: any;
}

export const initialState: SurveyState = {
  survey: {} as Survey,
  instances: [] as SurveyInstance[],
  singlePreviewInstance: {} as SurveyInstancePreview,
  error: undefined
};
