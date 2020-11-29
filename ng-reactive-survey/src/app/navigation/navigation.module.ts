import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { SecurityModule } from '../security/security.module';
import { RouterModule } from '@angular/router';

const modules = [
  SharedModule,
  SecurityModule,
  RouterModule,
  MatCardModule,
  MatButtonModule
];

const components = [
  NavBarComponent
];

@NgModule({
  declarations: [...components],
  exports: [
    NavBarComponent
  ],
  imports: [...modules]
})
export class NavigationModule {}
