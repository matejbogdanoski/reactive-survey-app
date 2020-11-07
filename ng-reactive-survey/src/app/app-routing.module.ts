import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'survey',
    loadChildren: () => import('./survey/survey.module').then(m => m.SurveyModule)
  },
  {
    path: 'take-survey',
    loadChildren: () => import('./survey-renderer/survey-renderer.module').then(m => m.SurveyRendererModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
