import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SurveyQuestionCreateComponent } from './survey-question-create/survey-question-create.component';
import { StoreModule } from '@ngrx/store';
import { reducers } from './reducers';
import { SurveyRoutingModule } from './survey-routing.module';
import { SurveyCreateComponent } from './survey-create/survey-create.component';
import { EffectsModule } from '@ngrx/effects';
import { SurveyQuestionCreateEffects } from './survey-question-create/survey-question-create.effects';
import { SurveyQuestionService } from './services/survey-question.service';
import { ReactiveFormsModule } from '@angular/forms';
import { SurveyCreateEffects } from './survey-create/survey-create.effects';
import { SurveyService } from './services/survey.service';

const components = [
  SurveyQuestionCreateComponent,
  SurveyCreateComponent
];

const services = [
  SurveyQuestionService,
  SurveyService
];

const effects = [
  SurveyQuestionCreateEffects,
  SurveyCreateEffects
];

@NgModule({
  providers: [...services],
  declarations: [...components],
  imports: [
    CommonModule,
    StoreModule.forFeature('survey', reducers),
    SurveyRoutingModule,
    EffectsModule.forFeature(effects),
    ReactiveFormsModule
  ]
})
export class SurveyModule {}
