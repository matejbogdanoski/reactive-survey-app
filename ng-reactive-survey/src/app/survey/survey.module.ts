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

const components = [
  SurveyQuestionCreateComponent,
  SurveyCreateComponent,
  SurveyQuestionOptionsCreateComponent,
  SurveyResponsesComponent
];

const services = [
  SurveyQuestionService,
  SurveyService,
  SurveyQuestionOptionService
];

const pages = [
  SurveyEditPage,
  HomePage
];

const modules = [
  SharedModule,
  SurveyRoutingModule,
  ReactiveFormsModule,
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
  MatTabsModule
];

const pipes = [
  QuestionTypeIconPipe
];

const store = [
  StoreModule.forFeature(surveyModuleKey, surveyReducer),
  EffectsModule.forFeature([SurveyEffects])
];

@NgModule({
  providers: [...services],
  declarations: [...components, ...pages, ...pipes],
  imports: [...modules, ...store]
})
export class SurveyModule {}
