import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { SurveyEditPage } from './pages/survey-edit/survey-edit.page';
import { HomePage } from './pages/home/home.page';
import { TakenSurveysListPage } from './pages/taken-surveys-list/taken-surveys-list.page';

const routes: Route[] = [
  {
    path: 'my-surveys',
    component: HomePage
  },
  {
    path: 'taken-surveys',
    component: TakenSurveysListPage
  },
  {
    path: 'edit/:id',
    component: SurveyEditPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SurveyRoutingModule {}
