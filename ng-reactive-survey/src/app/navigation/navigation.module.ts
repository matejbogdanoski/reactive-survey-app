import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

const modules = [
  SharedModule
];

const components = [
  NavBarComponent
];

@NgModule({
  declarations: [...components],
  exports: [
    NavBarComponent
  ],
  imports: [...modules, MatCardModule, MatButtonModule]
})
export class NavigationModule {}
