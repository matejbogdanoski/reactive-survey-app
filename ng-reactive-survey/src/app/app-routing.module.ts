import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './security/guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/survey',
    pathMatch: 'full'
  },
  {
    path: 'survey',
    loadChildren: () => import('./survey/survey.module').then(m => m.SurveyModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'take-survey',
    loadChildren: () => import('./take-survey/take-survey.module').then(m => m.TakeSurveyModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'authenticate',
    loadChildren: () => import('./security/security.module').then(m => m.SecurityModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
