import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SurveyQuestionCreateComponent } from './components/survey-question-create/survey-question-create.component';
import { StoreModule } from '@ngrx/store';
import { SurveyRoutingModule } from './survey-routing.module';
import { SurveyCreateComponent } from './components/survey-create/survey-create.component';
import { EffectsModule } from '@ngrx/effects';
import { SurveyQuestionService } from './services/survey-question.service';
import { ReactiveFormsModule } from '@angular/forms';
import { SurveyService } from './services/survey.service';
import { SurveyEditPage } from './pages/survey-edit/survey-edit.page';
import { HomePage } from './pages/home/home.page';
import { SurveyEffects } from './store/survey.effects';
import { surveyReducer } from './store/survey.reducers';

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

@NgModule({
  providers: [...services],
  declarations: [...components, ...pages],
  imports: [
    CommonModule,
    StoreModule.forFeature('survey', surveyReducer),
    SurveyRoutingModule,
    EffectsModule.forFeature([SurveyEffects]),
    ReactiveFormsModule
  ]
})
export class SurveyModule {}
