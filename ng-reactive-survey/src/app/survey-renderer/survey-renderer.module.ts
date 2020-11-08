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
import { MatCardModule } from '@angular/material/card';
import { MatRadioModule } from '@angular/material/radio';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

const modules = [
  SharedModule,
  SurveyRendererRoutingModule,
  ReactiveFormsModule,
  MatFormFieldModule,
  MatSelectModule,
  MatInputModule,
  MatButtonModule,
  MatCheckboxModule,
  MatIconModule,
  MatCardModule,
  MatRadioModule,
  MatDatepickerModule,
  MatNativeDateModule,
  NgxMaterialTimepickerModule
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

const materialProviders = [
  MatDatepickerModule
]

@NgModule({
  providers: [...services, ...materialProviders],
  declarations: [...components, ...pages],
  imports: [...modules, ...store]
})
export class SurveyRendererModule {}
