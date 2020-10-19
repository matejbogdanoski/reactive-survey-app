import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { SurveyState } from '../../store/survey.state';
import { createSurvey } from './home.actions';

@Component({
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit {

  constructor(
    private _store: Store<SurveyState>
  ) { }

  ngOnInit(): void {
  }

  onCreateSurvey() {
    this._store.dispatch(createSurvey());
  }

}
