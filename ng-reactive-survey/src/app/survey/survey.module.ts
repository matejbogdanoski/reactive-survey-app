import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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

const components = [
  SurveyQuestionCreateComponent,
  SurveyCreateComponent
];

const services = [
  SurveyQuestionService,
  SurveyService
];

const pages = [
  SurveyEditPage,
  HomePage
];

const modules = [
  CommonModule,
  SurveyRoutingModule,
  ReactiveFormsModule
];

const store = [
  StoreModule.forFeature(surveyModuleKey, surveyReducer),
  EffectsModule.forFeature([SurveyEffects])
];

@NgModule({
  providers: [...services],
  declarations: [...components, ...pages],
  imports: [...modules, ...store]
})
export class SurveyModule {}
