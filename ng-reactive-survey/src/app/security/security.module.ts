import { NgModule } from '@angular/core';
import { AuthenticationService } from './services/authentication/authentication.service';
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
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { UserService } from './services/user/user.service';
import { MatSidenavModule } from '@angular/material/sidenav';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { MatListModule } from '@angular/material/list';

const services = [
  AuthenticationService,
  AuthGuard,
  UserService
];

const modules = [
  SharedModule,
  SecurityRoutingModule,
  ReactiveFormsModule
];

const material = [
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule,
  MatSidenavModule,
  MatListModule
];

const store = [
  StoreModule.forFeature(securityModuleKey, securityReducer),
  EffectsModule.forFeature([SecurityEffects])
];

const components = [
  LoginComponent,
  RegisterComponent,
  EditProfileComponent,
  ResetPasswordComponent
];

@NgModule({
  declarations: [...components],
  providers: [...services],
  imports: [...modules, ...store, ...material]
})
export class SecurityModule {}
