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

const components = [
  SurveyQuestionCreateComponent
];

const services = [
  SurveyQuestionService
];

@NgModule({
  providers: [...services],
  declarations: [...components, SurveyCreateComponent],
  imports: [
    CommonModule,
    StoreModule.forFeature('survey', reducers),
    SurveyRoutingModule,
    EffectsModule.forFeature([SurveyQuestionCreateEffects])
  ]
})
export class SurveyModule {}
