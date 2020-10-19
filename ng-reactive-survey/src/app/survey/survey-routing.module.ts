import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { SurveyEditPage } from './pages/survey-edit/survey-edit.page';
import { HomePage } from './pages/home/home.page';

const routes: Route[] = [
  {
    path: '',
    component: HomePage
  },
  {
    path: ':naturalKey',
    component: SurveyEditPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SurveyRoutingModule {}
