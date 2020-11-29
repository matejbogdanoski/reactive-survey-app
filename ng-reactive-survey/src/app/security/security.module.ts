import { NgModule } from '@angular/core';
import { AuthenticationService } from './services/authentication.service';
import { SharedModule } from '../shared/shared.module';
import { StoreModule } from '@ngrx/store';
import { securityModuleKey, securityReducer } from './store/security.reducers';
import { EffectsModule } from '@ngrx/effects';
import { SecurityEffects } from './store/security.effects';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { SecurityRoutingModule } from './security-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthGuard } from './guard/auth.guard';

const services = [
  AuthenticationService,
  AuthGuard
];

const modules = [
  SharedModule,
  SecurityRoutingModule,
  ReactiveFormsModule
];

const material = [
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule
];

const store = [
  StoreModule.forFeature(securityModuleKey, securityReducer),
  EffectsModule.forFeature([SecurityEffects])
];

const components = [
  LoginComponent,
  RegisterComponent
];

@NgModule({
  declarations: [...components],
  providers: [...services],
  imports: [...modules, ...store, ...material]
})
export class SecurityModule {}
