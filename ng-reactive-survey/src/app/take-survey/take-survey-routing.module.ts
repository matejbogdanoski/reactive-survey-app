import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { TakeSurveyPage } from './pages/take-survey/take-survey.page';

const routes: Route[] = [
  {
    path: ':naturalKey',
    component: TakeSurveyPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TakeSurveyRoutingModule {}
