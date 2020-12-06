import { NgModule } from '@angular/core';
import { SurveyQuestionCreateComponent } from './components/survey-question-create/survey-question-create.component';
import { StoreModule } from '@ngrx/store';
import { SurveyRoutingModule } from './survey-routing.module';
import { SurveyCreateComponent } from './components/survey-create/survey-create.component';
import { EffectsModule } from '@ngrx/effects';
import { SurveyQuestionService } from './services/survey-question/survey-question.service';
import { ReactiveFormsModule } from '@angular/forms';
import { SurveyService } from './services/survey/survey.service';
import { SurveyEditPage } from './pages/survey-edit/survey-edit.page';
import { HomePage } from './pages/home/home.page';
import { SurveyEffects } from './store/survey.effects';
import { surveyModuleKey, surveyReducer } from './store/survey.reducers';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { SharedModule } from '../shared/shared.module';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { SurveyQuestionOptionsCreateComponent } from './components/survey-question-options-create/survey-question-options-create.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { QuestionTypeIconPipe } from './pipes/question-type-icon.pipe';
import { SurveyQuestionOptionService } from './services/survey-question-option/survey-question-option.service';
import { SurveyResponsesComponent } from './components/survey-responses/survey-responses.component';
import { MatTabsModule } from '@angular/material/tabs';
import { SurveyResponsesSummaryComponent } from './components/survey-responses-summary/survey-responses-summary.component';
import { SurveyResponsesIndividualComponent } from './components/survey-responses-individual/survey-responses-individual.component';
import { ChartsModule } from 'ng2-charts';
import { ChartDataPipe } from './pipes/chart/chart-data.pipe';
import { ChartLabelsPipe } from './pipes/chart/chart-labels.pipe';
import { MatPaginatorModule } from '@angular/material/paginator';
import { TakeSurveyModule } from '../take-survey/take-survey.module';
import { MySurveysListComponent } from './components/my-surveys-list/my-surveys-list.component';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TakenSurveysListPage } from './pages/taken-surveys-list/taken-surveys-list.page';
import { SingleInstancePreviewPage } from './pages/single-instance-preview/single-instance-preview.page';
import { InvitationsDialog } from './dialogs/invitations/invitations.dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { SurveyInvitationsService } from './services/survey-invitations/survey-invitations.service';

const components = [
  SurveyQuestionCreateComponent,
  SurveyCreateComponent,
  SurveyQuestionOptionsCreateComponent,
  SurveyResponsesComponent,
  SurveyResponsesSummaryComponent,
  SurveyResponsesIndividualComponent,
  MySurveysListComponent
];

const services = [
  SurveyQuestionService,
  SurveyService,
  SurveyQuestionOptionService,
  SurveyInvitationsService
];

const pages = [
  SurveyEditPage,
  HomePage,
  TakenSurveysListPage,
  SingleInstancePreviewPage
];

const modules = [
  SharedModule,
  SurveyRoutingModule,
  ReactiveFormsModule,
  ChartsModule,
  TakeSurveyModule
];

const material = [
  MatTableModule,
  MatFormFieldModule,
  MatSelectModule,
  MatInputModule,
  MatButtonModule,
  MatCheckboxModule,
  MatIconModule,
  MatCardModule,
  MatTooltipModule,
  MatSlideToggleModule,
  DragDropModule,
  MatTabsModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatDialogModule
];

const pipes = [
  QuestionTypeIconPipe,
  ChartDataPipe,
  ChartLabelsPipe
];

const dialogs = [
  InvitationsDialog
];

const store = [
  StoreModule.forFeature(surveyModuleKey, surveyReducer),
  EffectsModule.forFeature([SurveyEffects])
];

@NgModule({
  providers: [...services],
  declarations: [...components, ...pages, ...pipes, ...dialogs],
  imports: [...modules, ...store, ...material]
})
export class SurveyModule {}
