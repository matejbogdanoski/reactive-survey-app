import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SurveyQuestionCreateComponent } from './survey-question-create/survey-question-create.component';
import { StoreModule } from '@ngrx/store';
import { reducers } from './reducers';
import { SurveyRoutingModule } from './survey-routing.module';
import { SurveyCreateComponent } from './survey-create/survey-create.component';

const components = [
  SurveyQuestionCreateComponent
];

@NgModule({
  declarations: [...components, SurveyCreateComponent],
  imports: [
    CommonModule,
    StoreModule.forFeature('survey', reducers),
    SurveyRoutingModule
  ]
})
export class SurveyModule {}
