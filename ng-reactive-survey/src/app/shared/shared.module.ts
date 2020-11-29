import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { SurveyInstanceService } from './services/survey-instance/survey-instance.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from '../security/interceptors/token-interceptor';
import { CookieService } from 'ngx-cookie-service';

const services = [
  SurveyInstanceService,
  CookieService
];

const modules = [
  CommonModule,
  TranslateModule
];

const interceptor = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }
];

@NgModule({
  providers: [...services, ...interceptor],
  exports: [...modules]
})
export class SharedModule {}
