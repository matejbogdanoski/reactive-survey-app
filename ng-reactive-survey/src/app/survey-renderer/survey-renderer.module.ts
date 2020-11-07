import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { StoreModule } from '@ngrx/store';
import { surveyRendererModuleKey, surveyRendererReducer } from './store/survey-renderer.reducers';
import { EffectsModule } from '@ngrx/effects';
import { SurveyRendererEffects } from './store/survey-renderer.effects';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { SurveyRendererComponent } from './components/survey-renderer/survey-renderer.component';
import { TakeSurveyPage } from './pages/take-survey/take-survey.page';
import { SurveyRendererRoutingModule } from './survey-renderer-routing.module';
import { SurveyRendererService } from './services/survey-renderer.service';

const modules = [
  SharedModule,
  SurveyRendererRoutingModule,
  ReactiveFormsModule,
  MatFormFieldModule,
  MatSelectModule,
  MatInputModule,
  MatButtonModule,
  MatCheckboxModule,
  MatIconModule
];

const store = [
  StoreModule.forFeature(surveyRendererModuleKey, surveyRendererReducer),
  EffectsModule.forFeature([SurveyRendererEffects])
];

const components = [
  SurveyRendererComponent
];

const pages = [
  TakeSurveyPage
];

const services = [
  SurveyRendererService
];

@NgModule({
  providers: [...services],
  declarations: [...components, ...pages],
  imports: [...modules, ...store]
})
export class SurveyRendererModule {}
