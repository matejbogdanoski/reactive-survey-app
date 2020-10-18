import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { SurveyCreateComponent } from './survey-create/survey-create.component';

const routes: Route[] = [
  {
    path: '',
    component: SurveyCreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SurveyRoutingModule {}
