import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { SurveyQuestionCreateComponent } from './survey-question-create/survey-question-create.component';

const routes: Route[] = [
  {
    path: '',
    component: SurveyQuestionCreateComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SurveyRoutingModule {}
