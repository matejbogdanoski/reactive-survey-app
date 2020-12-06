import { Survey } from '../../interfaces/survey.interface';
import { SurveyInstance } from '../../interfaces/survey-instance.interface';
import { SurveyInstancePreview } from '../../interfaces/survey-instance-preview.interface';
import { SurveyInvitation } from '../interfaces/survey-invitation.interface';

export interface SurveyState {
  survey: Survey;
  instances: SurveyInstance[];
  singlePreviewInstance: SurveyInstancePreview;
  surveyInvitations: SurveyInvitation[],
  error: any;
}

export const initialState: SurveyState = {
  survey: {} as Survey,
  instances: [] as SurveyInstance[],
  singlePreviewInstance: {} as SurveyInstancePreview,
  surveyInvitations: [] as SurveyInvitation[],
  error: undefined
};
