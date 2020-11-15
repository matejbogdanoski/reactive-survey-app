import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { SurveyInstanceService } from './services/survey-instance/survey-instance.service';

const services = [
  SurveyInstanceService
];

const modules = [
  CommonModule,
  TranslateModule
];

@NgModule({
  providers: [...services],
  exports: [...modules]
})
export class SharedModule {}
